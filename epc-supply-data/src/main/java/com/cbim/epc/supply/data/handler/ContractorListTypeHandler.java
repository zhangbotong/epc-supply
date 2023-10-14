package com.cbim.epc.supply.data.handler;


import com.cbim.epc.supply.data.domain.Contractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 合同承包人列表-类型转换器
 *
 * @author xiaozp
 * @since 2023/4/4
 */
public class ContractorListTypeHandler extends BaseTypeHandler<List<Contractor>> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Contractor> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, OBJECT_MAPPER.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error while converting List<Contractor> to JSON", e);
        }
    }

    @Override
    public List<Contractor> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return json == null ? null : parseJson(json);
    }

    @Override
    public List<Contractor> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return json == null ? null : parseJson(json);
    }

    @Override
    public List<Contractor> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return json == null ? null : parseJson(json);
    }

    private List<Contractor> parseJson(String json) throws SQLException {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<Contractor>>() {});
        } catch (JsonProcessingException e) {
            throw new SQLException("Error while parsing JSON to List<Contractor>", e);
        }
    }
}
