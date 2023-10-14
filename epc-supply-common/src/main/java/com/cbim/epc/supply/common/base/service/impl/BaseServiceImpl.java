package com.cbim.epc.supply.common.base.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.supply.common.base.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 基础服务实现
 *
 * @author xiaozp
 * @since 2023/04/26
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFields(Serializable id, T updatedEntity) {
        if (updatedEntity == null) {
            throw new IllegalArgumentException("Updated entity cannot be null.");
        }
        Class<T> entityClass = getEntityClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);

        // 获取主键在数据库中的列名
        assert tableInfo != null;
        String primaryKeyColumn = tableInfo.getKeyColumn();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ").append(tableInfo.getTableName()).append(" SET ");

        List<Object> parameters = new ArrayList<>();
        tableInfo.getFieldList().forEach(fieldInfo -> {
            String fieldName = fieldInfo.getProperty();
            try {
                Field field = getFieldIncludingParentClasses(entityClass, fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(updatedEntity);
                if ("updateDate".equals(fieldName)) {
                    sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                    parameters.add(LocalDateTime.now());
                }
                else if ("updateBy".equals(fieldName)) {
                    sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                    parameters.add(getUserId());
                }
                else if ("updateName".equals(fieldName)) {
                    sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                    parameters.add(getUserName());
                }
                else if (ObjectUtil.isNotEmpty(fieldValue)) {
                    sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                    Class<?> type = field.getType();
                    if (ClassUtil.isBasicType(type)
                            || ClassUtil.equals(type, "java.lang.String", false)
                            || ClassUtil.equals(type, "java.time.LocalDateTime", false)
                            || ClassUtil.equals(type, "java.time.LocalDate", false)
                            || ClassUtil.equals(type, "java.time.LocalTime", false)
                            || ClassUtil.equals(type, "java.util.Date", false)) {
                        parameters.add(fieldValue);
                    } else {
                        parameters.add(objectMapper.writeValueAsString(fieldValue));
                    }
                }
                else {
                    sqlBuilder.append(fieldInfo.getColumn()).append(" = null, ");
                }
            } catch (NoSuchFieldException | IllegalAccessException | JsonProcessingException e) {
                throw new RuntimeException("Error while updating field: " + fieldName, e);
            }
        });

        sqlBuilder.setLength(sqlBuilder.length() - 2); // Remove trailing comma
        sqlBuilder.append(" WHERE ").append(primaryKeyColumn).append(" = ?");

        parameters.add(id);

        String sql = sqlBuilder.toString();
        log.info("updateFields Executing SQL: {} \n with parameters: {}", sql, parameters);
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            return preparedStatement;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public <U> void updateFieldsToNull(Serializable id, Func1<T, U>... fields) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("Fields cannot be empty.");
        }
        Class<T> entityClass = getEntityClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);

        // 获取主键在数据库中的列名
        assert tableInfo != null;
        String primaryKeyColumn = tableInfo.getKeyColumn();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ").append(tableInfo.getTableName()).append(" SET ");

        List<Object> parameters = new ArrayList<>();
        tableInfo.getFieldList().forEach(fieldInfo -> {
            String fieldName = fieldInfo.getProperty();
            try {
                Field field = getFieldIncludingParentClasses(entityClass, fieldName);
                field.setAccessible(true);
                switch (fieldName) {
                    case "updateDate":
                        sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                        parameters.add(LocalDateTime.now());
                        break;
                    case "updateBy":
                        sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                        parameters.add(getUserId());
                        break;
                    case "updateName":
                        sqlBuilder.append(fieldInfo.getColumn()).append(" = ?, ");
                        parameters.add(getUserName());
                        break;
                    default:
                        break;
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Error while updating field: " + fieldName, e);
            }
        });

        Arrays.stream(fields).forEach(field -> {
            String fieldName = getFieldNameByFunc(entityClass, field);
            TableFieldInfo fieldInfo = getFieldInfoByFieldName(tableInfo, fieldName);
            sqlBuilder.append(fieldInfo.getColumn()).append(" = null, ");
        });

        sqlBuilder.setLength(sqlBuilder.length() - 2); // Remove trailing comma
        sqlBuilder.append(" WHERE ").append(primaryKeyColumn).append(" = ?");

        parameters.add(id);

        String sql = sqlBuilder.toString();
        log.info("updateFieldsToNull Executing SQL: {} \n with parameters: {}", sql, parameters);
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            return preparedStatement;
        });
    }

    /**
     * 增加判空操作，避免传入空集合时出现 in ( ) 的情况报错
     * @param idList 主键ID列表
     */
    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtil.isEmpty(idList)){
            return new ArrayList<>();
        }
        return super.listByIds(idList);
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        List<T> list = super.list(queryWrapper);
        return list == null ? new ArrayList<>() : list;
    }

    private Field getFieldIncludingParentClasses(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            }
            return getFieldIncludingParentClasses(superClass, fieldName);
        }
    }


    private <R> String getFieldNameByFunc(Class<T> entityClass, Func1<T, R> field) {
        SerializedLambda serializedLambda = LambdaUtil.resolve(field);
        String methodName = serializedLambda.getImplMethodName();
        return methodName.startsWith("get")
                ? methodName.substring(3, 4).toLowerCase() + methodName.substring(4)
                : methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
    }

    private TableFieldInfo getFieldInfoByFieldName(TableInfo tableInfo, String fieldName) {
        Optional<TableFieldInfo> fieldInfoOptional = tableInfo.getFieldList().stream()
                .filter(f -> f.getProperty().equals(fieldName))
                .findFirst();

        if (!fieldInfoOptional.isPresent()) {
            throw new RuntimeException("Field not found: " + fieldName);
        }

        return fieldInfoOptional.get();
    }

    private String getUserName() {
        if (Objects.isNull(UserContext.getUser())) {
            return "";
        }
        return UserContext.getUser().getName();
    }

    private Long getUserId() {
        if (Objects.isNull(UserContext.getUser())) {
            return -1L;
        }
        return UserContext.getUser().getId();
    }
}