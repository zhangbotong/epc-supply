package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.base.usercontext.UserDTO;
import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.EntDTO;
import com.cbim.epc.sdk.easyapi.metadto.ObjectDetailDTO;
import com.cbim.epc.sdk.mq.constants.EpcMQConstants;
import com.cbim.epc.sdk.mq.content.SimpleChangeDTO;
import com.cbim.epc.sdk.mq.utils.MQUtils;
import com.cbim.epc.supply.app.service.*;
import com.cbim.epc.supply.common.base.exception.EpcTemplateException;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.common.constant.AgreementPriceConstants;
import com.cbim.epc.supply.common.constant.ObjectCodeCountConstant;
import com.cbim.epc.supply.common.enums.*;
import com.cbim.epc.supply.common.mybatis.query.LambdaQueryWrapperX;
import com.cbim.epc.supply.data.domain.*;
import com.cbim.epc.supply.data.enums.BusinessType;
import com.cbim.epc.supply.data.mapper.SequenceMapper;
import com.cbim.epc.supply.data.mapper.SupplyMaterialAttributeMapper;
import com.cbim.epc.supply.data.mapper.SupplyMaterialMapper;
import com.cbim.epc.supply.data.mapper.ViewAgreementPriceIndexMapper;
import com.cbim.epc.supply.data.vo.req.MaterialParam;
import com.cbim.epc.supply.data.vo.req.MaterialQueryParam;
import com.cbim.epc.supply.data.vo.req.ProductQueryMaterialParam;
import com.cbim.epc.supply.data.vo.resp.MaterialResult;
import com.cbim.epc.supply.data.vo.resp.ProductMaterialVo;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialPriceVO;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 物料管理-服务实现
 */
@Slf4j
@Service
public class MaterialServiceImpl extends BaseServiceImpl<SupplyMaterialMapper, SupplyMaterial> implements MaterialService {

    @Value("${epc.pic.address}")
    private String picAddress;

    @Autowired
    private SupplyMaterialMapper materialMapper;


    @Autowired
    private MaterialAttributeService materialAttributeService;

    @Autowired
    private EasyApiUtil apiUtil;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private SupplyMaterialAttributeMapper materialAttributeMapper;

    @Autowired
    private MQUtils mqUtils;

    @Autowired
    private CommonService commonService;
    @Autowired private ViewAgreementPriceIndexMapper viewAgreementPriceIndexMapper;

    @Autowired
    @Lazy
    private AgreementPriceService agreementPriceService;

    @Autowired
    private MaterialPriceContractService contractPriceService;

    @Autowired
    private MaterialPriceTemporaryService temporaryPriceService;

    @Override
    public PageResponse<MaterialResult> selectMaterialPage(MaterialQueryParam queryParam) {
        queryParam = MaterialQueryParam.buildParam(queryParam);
        //返回参数
        Page<SupplyMaterial> materialPage = new Page<>();
        if("1".equals(queryParam.getIsPage())){
            materialPage.setSize(queryParam.getPageSize());
            materialPage.setCurrent(queryParam.getPageNum());
            //处理分页参数
            Integer pageNum = Optional.ofNullable(queryParam.getPageNum()).orElse(1);
            Integer pageSize = Optional.ofNullable(queryParam.getPageSize()).orElse(20);
            queryParam.setPageSize(pageSize);//此时重新赋值 --- 对应limit的值
            queryParam.setPageNum((pageNum - 1) * pageSize);//此时重新赋值 --- 对应offset得值
        }
        //处理排序字段
        String sortField = "updateDate".equals(queryParam.getSortField()) ? "update_date" : ("materialName".equals(queryParam.getSortField()) ? "material_name" : "reference_Count");
        queryParam.setSortField(sortField);
        //获取用户信息 供应商处理
        if("0".equals(queryParam.getIsQueryAll())){
            UserDTO user = UserContext.getUser();
            if (user != null) {
                // id:1648154231308562432 账户:14100000002  id:1648220708107722752  账户:14100000003 供应商信息
                if ("1648154231308562432".equals(user.getId().toString()) || "1648220708107722752".equals(user.getId().toString()))
                    queryParam.setVendorId(Objects.toString(user.getId()));
            }
        }
        //查询
        int count = materialMapper.queryCount(queryParam);
        materialPage.setTotal(count);
        if (count > 0) {
            List<SupplyMaterial> list = materialMapper.queryMaterialList(queryParam);
            materialPage.setRecords(list);
        } else {
            materialPage.setRecords(new ArrayList<>());
        }
        return PageResponse.buildSuccess(handleData(materialPage));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse saveOrUpdate(MaterialParam entity) {
        //操作类型  true:新增  false:修改
        boolean opType = Objects.isNull(entity.getId());
        //新增且出现小红点时  不允许复制
        if (opType && ChangesEnum.CHANGED.equals(ChangesEnum.getEnumByCode(entity.getHasChanges()))) {
            return SingleResponse.buildFailure("当前规则信息需要更新，无法复制");
        }
        //是否发布过
        Integer hasBeenPublished = entity.getHasBeenPublished();
        //发布时  数据进行校验
        if (PublishStatusEnum.PUBLISHED.equals(PublishStatusEnum.getEnumByCode(entity.getPublishStatus()))) {
            if (StrUtil.isBlank(entity.getVersion()) || ChangesEnum.CHANGED.equals(ChangesEnum.getEnumByCode(entity.getHasChanges())))
                entity.setVersion(Objects.toString(UUID.randomUUID().getLeastSignificantBits() * -1));
            if (StrUtil.isBlank(entity.getMaterialCode())) entity.setMaterialCode(geneCode(entity.getObjectTypeCode()));
            if (!HasBeenPublishedEnum.PUBLISHED.equals(HasBeenPublishedEnum.getEnumByCode(entity.getHasBeenPublished())))
                entity.setHasBeenPublished(HasBeenPublishedEnum.PUBLISHED.getCode());
            SingleResponse isPass = verifyParam(entity);
            if (isPass != null) return isPass;
        } else if (opType && !PublishStatusEnum.PUBLISHED.equals(PublishStatusEnum.getEnumByCode(entity.getPublishStatus()))) {
            //新增且非发布情况设置默认值为未发布
            entity.setHasBeenPublished(HasBeenPublishedEnum.NO_PUBLISH.getCode());
        }
        entity.setHasChanges(ChangesEnum.UNCHANGED.getCode());//规则主表小红点直接去掉
        entity.setVendorId(UserContext.getUser().getId());
        //用户信息处理
        UserDTO user = UserContext.getUser();
        if (user != null) {
            // id:1648154231308562432 账户:14100000002  id:1648220708107722752  账户:14100000003 供应商信息
            if ("1648154231308562432".equals(user.getId().toString())) entity.setVendorId(1648154231308562432L);
            if ("1648220708107722752".equals(user.getId().toString())) entity.setVendorId(1648220708107722752L);
        }
        Boolean isChange = false;
        //编辑
        if (opType) {
            //保存物料主表
            materialMapper.insert(entity);
        } else {
            /**
             * 如果未发布过直接删除子表数据
             * 原因：切换不同分类下的时候  有可能之前分类数据还存在 需要删除
             */
            if (HasBeenPublishedEnum.NO_PUBLISH.getCode().equals(hasBeenPublished)) {
                materialAttributeMapper.deleteByMap(MapUtil.builder("material_id", (Object) entity.getId()).build());
            }
            //编辑 + 发布时 + 已发布过 才有可能通知下游数据  当前代码必须放在主表更新语句前 原因是version改变了需要更新到主表里
            if (PublishStatusEnum.PUBLISHED.getCode().equals(entity.getPublishStatus()) && HasBeenPublishedEnum.PUBLISHED.getCode().equals(entity.getHasBeenPublished())) {
                isChange = compareDBData(entity);
            }
            //修改主表数据
            updateFields(entity.getId(), entity);
        }
        //子表不为空时在进行保存
        if (entity.getAttrList() != null && entity.getAttrList().size() > 0) {
            //无论新增还是编辑 族设置集合必须插入
            entity.getAttrList().stream().forEach(v -> {
                v.setMaterialId(entity.getId());
                v.setHasChanges(ChangesEnum.UNCHANGED.getCode());//直接去掉属性里面的小红点
            });
            materialAttributeService.saveOrUpdateBatch(entity.getAttrList());
        }
        //存在变化时发消息给下游
        if (isChange) {
            SimpleChangeDTO message = new SimpleChangeDTO();
            message.setId(Objects.toString(entity.getId()));
            message.setVersion(Objects.toString(entity.getVersion()));
            mqUtils.sendSimpleChangeMessage(EpcMQConstants.KEY_SUPPLY_MATERIAL_CHANGE, message);
        }
        //元数据和form表单做比较  不一致时  标记小红点
        compareMetaData(entity);
        return SingleResponse.buildSuccess(entity.getId(), opType ? "新增成功" : "修改成功");
    }

    @Override
    public MaterialResult selectById(Long id, String operationType) {
        MaterialResult result = new MaterialResult();
        //获取主表数据
        SupplyMaterial source = materialMapper.selectById(id);
        if (source == null) {
            return null;
        }
        //处理企业
        List<EntDTO> entList = apiUtil.getEpcEnts();
        Map<String, String> entMap = entList.stream().collect(Collectors.toMap(EntDTO::getEntCode, EntDTO::getEntName));
        source.setApplicableScopeName(entMap.get(source.getApplicableScopeCode()));

        if (CollectionUtil.isNotEmpty(source.getFileInfo())) {
            source.getFileInfo().forEach(e -> e.setUrl(CharSequenceUtil.concat(false, picAddress, "?id=", e.getId())));
        }
        if (CollectionUtil.isNotEmpty(source.getThumbnail())) {
            source.getThumbnail().forEach(e -> e.setUrl(CharSequenceUtil.concat(false, picAddress, "?id=", e.getId())));
        }
        BeanUtils.copyProperties(source, result);
        //获取属性数据
        List<GroupInfo> groupInfos = new ArrayList<>();
        CollectionUtil.emptyIfNull(materialAttributeService.selectByMaterialId(id))
                .stream()
                .collect(Collectors.groupingBy(attributeVO -> Pair.of(attributeVO.getGroupCode(), attributeVO.getGroupName())))
                .forEach((k, v) -> groupInfos.add(new GroupInfo().setGroupCode(k.getFirst()).setGroupName(k.getSecond()).setAttrList(v)));
        result.setGroupList(groupInfos);
        //设置价格
        Map<String, Object> minAndMaxAgreementPrice = agreementPriceService.minAndMaxAgreementPrice(source.getId());
        result.setAgreementPriceValueType((String) minAndMaxAgreementPrice.get("type"));
        result.setAgreementPriceMin((BigDecimal) minAndMaxAgreementPrice.get("min"));
        result.setAgreementPriceMax((BigDecimal) minAndMaxAgreementPrice.get("max"));
        result.setContractPriceAverage(contractPriceService.contractPriceAverage(source.getId()));
        result.setMarketPriceAverage(temporaryPriceService.getAvgPrice(source.getId()).getAvgTempPrice());
        //设置供应商
        List<SupplyVendorVO> agreementPriceVendors = agreementPriceService.getAgreementPriceVendor(source.getId());
        List<SupplyVendorVO> contractPriceVendors = contractPriceService.getContractPriceVendor(source.getId());
        List<SupplyVendorVO> temporaryPriceVendors = temporaryPriceService.getTemporaryPriceVendor(source.getId());
        result.setSupplyVendors(CollectionUtil.unionAll(agreementPriceVendors,contractPriceVendors,temporaryPriceVendors));
        init(result, operationType);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        try {
            //删除属性数据
            materialAttributeService.deleteByMaterialId(id);
            UpdateWrapper<SupplyMaterial> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", id);
            SupplyMaterial supplyMaterial = new SupplyMaterial();
            supplyMaterial.setIsValid(ValidEnum.INVALID.getCode());
            return materialMapper.update(supplyMaterial, wrapper);
        } catch (DataIntegrityViolationException e) {
            throw new EpcTemplateException("当前物料已被其它应用引用,无法删除");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int off(Long id, Integer isOff) {
        SupplyMaterial material = materialMapper.selectById(id);
        material.setPublishStatus(isOff);
        return materialMapper.updateById(material);
    }

    /**
     * 验证数据
     */
    private SingleResponse verifyParam(MaterialParam entity) {
        if (Strings.isBlank(entity.getObjectTypeCode())) {
            return SingleResponse.buildFailure("物料分类不能为空");
        }
        if (Strings.isBlank(entity.getApplicableScopeCode())) {
            return SingleResponse.buildFailure("适用范围不能为空");
        }
        if (Strings.isBlank(entity.getMeasureUnitCode())) {
            return SingleResponse.buildFailure("计量单位不能为空");
        }
        if (Strings.isBlank(entity.getMaterialName())) {
            return SingleResponse.buildFailure("物料名称不能为空");
        }
        if (entity.getAttrList() == null || entity.getAttrList().size() == 0) {
            return SingleResponse.buildFailure("物料属性设置不能为空");
        }
        //该物料已经存在
        LambdaQueryWrapperX wrapper = new LambdaQueryWrapperX<SupplyMaterial>()
                .neIfPresent(SupplyMaterial::getId, entity.getId())
                .eq(SupplyMaterial::getApplicableScopeCode, entity.getApplicableScopeCode())
                .eq(SupplyMaterial::getObjectTypeCode, entity.getObjectTypeCode())
                .eq(SupplyMaterial::getMaterialFullName, entity.getMaterialFullName())
                .eq(SupplyMaterial::getPublishStatus, PublishStatusEnum.PUBLISHED.getCode())
                .eq(SupplyMaterial::getIsValid, ValidEnum.VALID.getCode());
        List exist = materialMapper.selectList(wrapper);
        if (exist != null && exist.size() > 0) {
            return SingleResponse.buildFailure("该物料已经存在");
        }
        return null;
    }

    /**
     * 处理数据
     */
    private Page<MaterialResult> handleData(Page<SupplyMaterial> materialPage) {
        //返回值
        Page<MaterialResult> resPage = JSONObject.parseObject(JSON.toJSONString(materialPage), new TypeReference<Page<MaterialResult>>() {
        });
        //处理企业
        List<EntDTO> entList = apiUtil.getEpcEnts();
        Map<String, String> entMap = entList.stream().collect(Collectors.toMap(EntDTO::getEntCode, EntDTO::getEntName));
        resPage.getRecords().stream().forEach(v -> {
            if (v.getThumbnail() != null && v.getThumbnail().size() > 0) {
                FileInfo fileInfo = v.getThumbnail().stream().filter(f -> "1".equals(Objects.toString(f.getIsMain()))).findFirst().orElse(null);
                if (fileInfo != null) {
                    v.setMainPic(String.format("%s?id=%s", picAddress, fileInfo.getId()));
                }
            }
            v.setApplicableScopeName(entMap.get(v.getApplicableScopeCode()));
            //如果时间为空则表示是其他供应商或者管理员录入的数据
            if (v.getUpdateDate() == null) {
                v.setShowBtn("0");//此时不用展示按钮
            } else {
                v.setShowBtn("1");
            }
        });
        return resPage;
    }

    @Override
    public SingleResponse toEdit(Long id) {
        MaterialResult res = new MaterialResult();
        SupplyMaterial supplyMaterial = materialMapper.selectById(id);
        if (supplyMaterial == null) {
            SingleResponse.buildFailure("该条数据已被删除");
        }
        BeanUtils.copyProperties(supplyMaterial, res);
        //处理图片
        if (CollectionUtil.isNotEmpty(supplyMaterial.getFileInfo())) {
            supplyMaterial.getFileInfo().forEach(e -> e.setUrl(CharSequenceUtil.concat(false, picAddress, "?id=", e.getId())));
        }
        if (CollectionUtil.isNotEmpty(supplyMaterial.getThumbnail())) {
            supplyMaterial.getThumbnail().forEach(e -> e.setUrl(CharSequenceUtil.concat(false, picAddress, "?id=", e.getId())));
        }
        //查询对象的属性
        ObjectAttr objectAttr = commonService.getObjectAttr(supplyMaterial.getObjectTypeCode(), supplyMaterial.getApplicableScopeCode());
        List<GroupInfo> groupInfos = new ArrayList<>();
        if (objectAttr != null && objectAttr.getGroupList() != null && objectAttr.getGroupList().size() > 0) {
            res.setObjVersion(objectAttr.getObjVersion());
            res.setObjectId(Long.valueOf(objectAttr.getObjectId()));
            List<SupplyMaterialAttribute> objAttrList = new ArrayList<>();
            objectAttr.getGroupList().stream().forEach(v -> {
                objAttrList.addAll(v.getAttrList());
            });
            List<SupplyMaterialAttribute> attributeList = materialAttributeMapper.selectByMap(MapUtil.builder("material_id", (Object) id).build());
            if (attributeList != null && attributeList.size() > 0) {
                Map<Long, SupplyMaterialAttribute> dbAttrMap = attributeList.stream().collect(Collectors.toMap(SupplyMaterialAttribute::getAttributeCode, Function.identity()));
                objAttrList.stream().forEach(v -> {
                    if (dbAttrMap.containsKey(v.getAttributeCode())) {
                        SupplyMaterialAttribute attribute = dbAttrMap.get(v.getAttributeCode());
                        v.setId(attribute.getId());
                        v.setChecks(attribute.getChecks());
                        v.setHasChanges(attribute.getHasChanges());
                        v.setMaterialId(attribute.getMaterialId());
                        if (ValueInputTypeEnum.INPUT_TYPE.getCode().equals(v.getValueInputType())) {
                            v.getAttributeValue().setInputValue(attribute.getAttributeValue().getInputValue());
                        }
                    }
                });
                //转换为groupList
                objAttrList.stream()
                        .collect(Collectors.groupingBy(attributeVO -> Pair.of(attributeVO.getGroupCode(), attributeVO.getGroupName())))
                        .forEach((k, v) -> groupInfos.add(new GroupInfo().setGroupCode(k.getFirst()).setGroupName(k.getSecond()).setAttrList(v)));

            }

        }
        res.setGroupList(groupInfos);
        return SingleResponse.buildSuccess(res);
    }

    @Override
    public Map<String, Integer> getObjectCount() {
        Map<String, Integer> map = new HashMap<>();
        String selectSql = ObjectCodeCountConstant.OBJECT_TYPE_CODE +
                ",count(1) as " +
                ObjectCodeCountConstant.OBJECT_TYPE_COUNT;
        List<Map<String, Object>> mapList = materialMapper.selectMaps(new QueryWrapper<SupplyMaterial>()
                .select(selectSql)
                .lambda()
                .eq(SupplyMaterial::getIsValid,ValidEnum.VALID.getCode())
                .groupBy(SupplyMaterial::getObjectTypeCode)
        );
        if (CollectionUtil.isNotEmpty(mapList)) {
            mapList.forEach(m -> {
                map.put(m.get(ObjectCodeCountConstant.OBJECT_TYPE_CODE).toString(),
                        Integer.valueOf(m.get(ObjectCodeCountConstant.OBJECT_TYPE_COUNT).toString())
                );
            });
        }
        return map;
    }

    @Override
    public List<SupplyMaterialPriceVO> getMaterialByCode(String objTypeCode, Integer subDate, String applicableCode) {
        if (subDate <= 0) return new ArrayList<>();
        List<SupplyMaterial> supplyMaterialList = materialMapper.selectList(Wrappers.lambdaQuery(SupplyMaterial.class)
                .likeRight(SupplyMaterial::getObjectTypeCode, objTypeCode)
                .eq(SupplyMaterial::getPublishStatus, PublishStatusEnum.PUBLISHED.getCode())
                .eq(StrUtil.isNotBlank(applicableCode), SupplyMaterial::getApplicableScopeCode, applicableCode)
                .select(SupplyMaterial::getMaterialCode,
                        SupplyMaterial::getMaterialFullName,
                        SupplyMaterial::getMaterialName,
                        SupplyMaterial::getSubData,
                        SupplyMaterial::getVendorId,
                        SupplyMaterial::getId
                ));
        if (CollUtil.isEmpty(supplyMaterialList)) {
            return new ArrayList<>();
        }
        // 构造属性
        List<Long> idList = supplyMaterialList.stream().map(SupplyMaterial::getId).collect(Collectors.toList());
        List<SupplyMaterialAttribute> materialAttributeList = materialAttributeMapper.selectList(Wrappers.lambdaQuery(SupplyMaterialAttribute.class)
                .in(SupplyMaterialAttribute::getMaterialId, idList));
        Map<Long, List<SupplyMaterialAttribute>> listMap = materialAttributeList.stream().collect(Collectors.groupingBy(SupplyMaterialAttribute::getMaterialId));
        return supplyMaterialList.stream().map(s -> {
            SupplyMaterialPriceVO materialPriceVO = new SupplyMaterialPriceVO();
            BeanUtils.copyProperties(s, materialPriceVO);
            materialPriceVO.setAttributeList(listMap.getOrDefault(s.getId(), new ArrayList<>()));
            return materialPriceVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SupplyMaterial> listByMaterialTypeCode(String materialTypeCode) {
        // 在已发布状态范围内查询
        List<SupplyMaterial> materialList = this.list(Wrappers.lambdaQuery(SupplyMaterial.class)
                .eq(SupplyMaterial::getPublishStatus, AgreementPriceConstants.MATERIAL_RELEASED_STATUS)
                .likeRight(SupplyMaterial::getMaterialCode, materialTypeCode)
        );
        // 集采价处理 subData 字段
        List<AgreementPriceIndex> agreementPriceIndexList = viewAgreementPriceIndexMapper.selectList(Wrappers.lambdaQuery(AgreementPriceIndex.class).eq(AgreementPriceIndex::getMaterialTypeCode, materialTypeCode));
        if (CollectionUtil.isEmpty(agreementPriceIndexList)){
            return materialList;
        }
        List<Long> materialIdList = agreementPriceIndexList.stream().map(AgreementPriceIndex::getMaterialId).collect(Collectors.toList());
        for (SupplyMaterial material : materialList) {
            if (materialIdList.contains(material.getId())) {
                material.setSubData(material.getSubData() | 1);
            }
        }
        return materialList;
    }

    private String geneCode(String rule) {
        //物料分类编码 + 六位流水号
        Integer initial = sequenceMapper.initial(BusinessType.MATERIAL.getName(), "", "");
        return String.format("%s-%s", rule, String.format("%06d", Integer.valueOf(initial)));
    }

    /**
     * 是否发生值变化  表单数据和数据库做比对  目的标记版本变化
     */
    private boolean compareDBData(MaterialParam entity) {
        boolean isChange = false;
        List<SupplyMaterialAttribute> formList = entity.getAttrList();
        List<SupplyMaterialAttribute> attributeList = materialAttributeMapper.selectByMap(MapUtil.builder("material_id", (Object) entity.getId()).build());
        //都为空时 无变化
        if ((attributeList == null || attributeList.size() == 0) && (formList == null || formList.size() == 0)) {
            return false;
        }
        //表单、数据库任一个为空则表示存在变化 或者两个数量不一致
        if (((attributeList == null || attributeList.size() == 0) && formList != null && formList.size() > 0) || ((formList == null || formList.size() == 0) && attributeList != null && attributeList.size() > 0) || (attributeList != null && formList != null && attributeList.size() != formList.size())) {
            isChange = true;
        } else {
            Map<Long, SupplyMaterialAttribute> familyMap = attributeList.stream().collect(Collectors.toMap(SupplyMaterialAttribute::getId, Function.identity()));
            for (SupplyMaterialAttribute v : formList) {
                if (familyMap.containsKey(v.getId())) {
                    SupplyMaterialAttribute dbFamily = familyMap.get(v.getId());
                    if (ValueInputTypeEnum.ENUM_TYPE.equals(ValueInputTypeEnum.getEnumByCode(v.getValueInputType()))) {
                        Map<String, NewAttributeValue.EnumValue> formEnumValues = v.getAttributeValue().getEnumValues();
                        Map<String, NewAttributeValue.EnumValue> dbEnumValues = dbFamily.getAttributeValue().getEnumValues();
                        Iterator<Map.Entry<String, NewAttributeValue.EnumValue>> iterator = formEnumValues.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, NewAttributeValue.EnumValue> next = iterator.next();
                            NewAttributeValue.EnumValue formValue = next.getValue();
                            if (dbEnumValues.containsKey(next.getKey())) {
                                NewAttributeValue.EnumValue dbValue = dbEnumValues.get(next.getKey());
                                if (!dbValue.getValue().equals(formValue.getValue())) {
                                    isChange = true;
                                    break;
                                }
                            } else {
                                //说明新增了个属性的值
                                isChange = true;
                                break;
                            }
                        }
                    } else if (ValueInputTypeEnum.RANGE_TYPE.equals(ValueInputTypeEnum.getEnumByCode(v.getValueInputType()))) {
                        Map<String, NewAttributeValue.RangeValue> formRangeValues = v.getAttributeValue().getRangeValue();
                        Map<String, NewAttributeValue.RangeValue> dbRangeValues = dbFamily.getAttributeValue().getRangeValue();
                        Iterator<Map.Entry<String, NewAttributeValue.RangeValue>> iterator = formRangeValues.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, NewAttributeValue.RangeValue> next = iterator.next();
                            NewAttributeValue.RangeValue formValue = next.getValue();
                            if (dbRangeValues.containsKey(next.getKey())) {
                                NewAttributeValue.RangeValue dbValue = dbRangeValues.get(next.getKey());
                                if (!dbValue.getMax().equals(formValue.getMax()) || dbValue.getMin().equals(formValue.getMin())) {
                                    isChange = true;
                                    break;
                                }
                            } else {
                                //说明新增了个属性的值
                                isChange = true;
                                break;
                            }
                        }
                    } else if ((ValueInputTypeEnum.INPUT_TYPE.equals(ValueInputTypeEnum.getEnumByCode(v.getValueInputType())))) {
                        if (dbFamily.getAttributeValue() != null && !dbFamily.getAttributeValue().getInputValue().equals(v.getAttributeValue().getInputValue())) {
                            isChange = true;
                            break;
                        }
                    }
                } else {
                    isChange = true;
                    break;
                }
            }
        }
        if (isChange) {
            entity.setVersion(Objects.toString(UUID.randomUUID().getLeastSignificantBits() * -1));
        }
        return isChange;
    }

    /**
     * 元数据是否发生变化 目的标记小红点
     */
    private void compareMetaData(MaterialParam entity) {
        ObjectDetailDTO objDetail = apiUtil.getObjectDetailByTypeCode(entity.getObjectTypeCode(), entity.getApplicableScopeCode());
        if (ObjectUtil.isNotEmpty(objDetail.getVersion()) && !objDetail.getVersion().equals(entity.getObjVersion())) {
            UpdateWrapper<SupplyMaterial> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", entity.getId());
            SupplyMaterial tplRule = new SupplyMaterial();
            tplRule.setHasChanges(ChangesEnum.CHANGED.getCode());
            materialMapper.update(tplRule, wrapper);
        }
    }

    /**
     * 字段初始化
     *
     * @param result
     * @param operationType
     */
    private void init(MaterialResult result, String operationType) {
        if (OperationTypeEnum.COPY.getCode().equals(operationType)) {
            result.setId(null);
            result.setVersion("");
            result.setHasBeenPublished(HasBeenPublishedEnum.NO_PUBLISH.getCode());
            result.setHasChanges(ChangesEnum.UNCHANGED.getCode());
            result.setMaterialCode("");
            result.setReferenceCount(0L);
            result.setPublishStatus(PublishStatusEnum.DRAFT.getCode());
            result.setCreateDate(null);
            result.setCreateName(null);
            result.setUpdateName(null);
            result.setUpdateDate(null);
            result.setCreateBy(null);
            result.setUpdateBy(null);
            result.getGroupList().forEach(v -> {
                v.getAttrList().forEach(a -> {
                    a.setId(null);
                    a.setMaterialId(null);
                    a.setHasChanges(ChangesEnum.UNCHANGED.getCode());
                });
            });
        }
    }


    /**
     * 处理属性和值得拼接
     * <p>
     * 属性项1：属性值1 | 属性值2,属性项2：属性值
     */
    private void handleAttrValueSplice(MaterialParam entity) {
        if (entity.getAttrList() != null && entity.getAttrList().size() > 0) {
            final String[] attrAndValue = {""};
            entity.getAttrList().stream().forEach(v -> {
                if (ValueInputTypeEnum.INPUT_TYPE.getCode().equals(v.getValueInputType())) {
                    NewAttributeValue attributeValue = v.getAttributeValue();
                    if (attributeValue != null && ObjectUtil.isNotEmpty(attributeValue.getInputValue())) {
                        attrAndValue[0] = attrAndValue[0] + "," + v.getAttributeName() + ":" + attributeValue.getInputValue();
                    }
                } else if (ValueInputTypeEnum.ENUM_TYPE.getCode().equals(v.getValueInputType())) {
                    NewAttributeValue attributeValue = v.getAttributeValue();
                    if (attributeValue != null && v.getChecks() != null && v.getChecks().size() > 0) {
                        Map<String, NewAttributeValue.EnumValue> enumValueMap = attributeValue.getEnumValues();
                        final String[] value = {""};
                        v.getChecks().stream().forEach(a -> {
                            NewAttributeValue.EnumValue enumValue = enumValueMap.get(a);
                            if (enumValue != null) {
                                value[0] = value[0] + "|" + enumValue.getValue();
                            }
                        });
                        if (ObjectUtil.isNotEmpty(value[0])) {
                            attrAndValue[0] = attrAndValue[0] + "," + v.getAttributeName() + ":" + value[0].substring(1);
                        }
                    }
                } else if (ValueInputTypeEnum.RANGE_TYPE.getCode().equals(v.getValueInputType())) {
                    NewAttributeValue attributeValue = v.getAttributeValue();
                    if (attributeValue != null && v.getChecks() != null && v.getChecks().size() > 0) {
                        Map<String, NewAttributeValue.RangeValue> enumValueMap = attributeValue.getRangeValue();
                        final String[] value = {""};
                        v.getChecks().stream().forEach(a -> {
                            NewAttributeValue.RangeValue enumValue = enumValueMap.get(a);
                            if (enumValue != null) {
                                value[0] = value[0] + "|" + enumValue.getMin() + "~" + enumValue.getMax();
                            }
                        });
                        if (ObjectUtil.isNotEmpty(value[0])) {
                            attrAndValue[0] = attrAndValue[0] + "," + v.getAttributeName() + ":" + value[0].substring(1);
                        }
                    }
                }
            });
            if (ObjectUtil.isNotEmpty(attrAndValue[0])) {
                entity.setAttributeValues(attrAndValue[0].substring(1));
            }

        }

    }


    /**
     * 根据物料分类编码和适用范围查询多个物料
     * @param param
     * @return
     */
    @Override
    public List<ProductMaterialVo> selectByObjectTypeCodeAndEntCode(ProductQueryMaterialParam param) {
        List<ProductMaterialVo> list = materialMapper.selectByObjectTypeCodeAndEntCode(param.getObjectCodes(), param.getEntCode());
        list.stream().forEach(v->{
            List<FileInfo> fileInfos = JSONObject.parseObject(v.getThumbnailUrl(), new TypeReference<List<FileInfo>>() {});
            if (fileInfos != null && fileInfos.size() > 0) {
                FileInfo fileInfo = fileInfos.stream().filter(f -> "1".equals(Objects.toString(f.getIsMain()))).findFirst().orElse(null);
                if (fileInfo != null) {
                    v.setThumbnailUrl(String.format("%s?id=%s", picAddress, fileInfo.getId()));
                }else{
                    v.setThumbnailUrl(null);
                }
            }
        });
        return list;
    }
}
