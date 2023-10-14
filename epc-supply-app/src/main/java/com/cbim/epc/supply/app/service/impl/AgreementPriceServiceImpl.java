package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.UnitDTO;
import com.cbim.epc.supply.app.service.*;
import com.cbim.epc.supply.common.base.exception.EpcSupplyException;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.common.constant.AgreementPriceConstants;
import com.cbim.epc.supply.data.domain.*;
import com.cbim.epc.supply.data.enums.PriceValueTypeEnum;
import com.cbim.epc.supply.data.mapper.MaterialAgreementPriceMapper;
import com.cbim.epc.supply.data.mapper.ViewAgreementPriceIndexMapper;
import com.cbim.epc.supply.data.vo.req.MaterialPriceQueryParam;
import com.cbim.epc.supply.data.vo.resp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kyrie
 * @description 针对表【epc_supply_material_agreement_price(供应资源管理-材料价格-集采协议表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class AgreementPriceServiceImpl extends BaseServiceImpl<MaterialAgreementPriceMapper, AgreementPrice>
        implements AgreementPriceService {
    private final MaterialService materialService;
    private final ViewAgreementPriceIndexMapper viewAgreementPriceIndexMapper;
    private final AgreementService agreementService;
    private final VendorService vendorService;
    private final MaterialAgreementPriceMapper materialAgreementPriceMapper;
    private final EasyApiUtil easyApiUtil;
    private final ExtService extService;
    private final DictService dictService;
    private final SupplyVendorService supplyVendorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdatePrice(List<AgreementPrice> agreementPriceList) {
        if (CollectionUtil.isEmpty(agreementPriceList)) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        for (AgreementPrice agreementPrice : agreementPriceList) {
            if (agreementPrice.getAgreementId() == null || agreementService.getById(agreementPrice.getAgreementId()) == null) {
                throw new EpcSupplyException("Supply-Agreement not exist");
            }
            agreementPrice.setUpdateBy(null);
            agreementPrice.setUpdateDate(now);
            agreementPrice.setUpdateName(null);
        }
        // 因为界面操作是以物料为整体的价格，如果删除了某条价格，提交过来无法直接体现，所以以物料维度，整体先删后插（更新价格会先删后插入id相同的价格，等同于更新）
        int delete = this.removeByMaterialId(agreementPriceList.get(0).getMaterialId());
        this.saveOrUpdateBatch(agreementPriceList);
        return agreementPriceList.size();
    }

    /**
     * 中文名脱敏处理
     *
     * @param name 中文名
     * @return 脱敏后的字符串
     */
    private String maskChineseName(String name) {
        if (name == null || name.length() < 2) {
            return name;
        } else if (name.length() == 2) {
            return name.charAt(0) + "*";
        } else {
            return name.charAt(0) +
                    "*" +
                    name.charAt(name.length() - 1);
        }
    }

    @Override
    public PageResponse<AgreementPriceIndexVO> search(MaterialPriceQueryParam queryParam) {
        Page<AgreementPriceIndex> priceAgreementIndexPage = viewAgreementPriceIndexMapper.selectPage(new Page<>(queryParam.getPageNum(), queryParam.getPageSize()),
                Wrappers.lambdaQuery(AgreementPriceIndex.class)
                        .likeRight(AgreementPriceIndex::getMaterialTypeCode, queryParam.getObjectTypeCode())
                        .and(queryParam.getMaterialNameOrCode() != null, wrapper -> wrapper
                                .like(AgreementPriceIndex::getMaterialCode, queryParam.getMaterialNameOrCode())
                                .or()
                                .like(AgreementPriceIndex::getMaterialName, queryParam.getMaterialNameOrCode()))
                        .orderByDesc(AgreementPriceIndex::getMaterialPublishStatus)
                        .orderByDesc(AgreementPriceIndex::getUpdateDate)
//                        .orderByAsc(AgreementPriceIndex::getMaterialCode)
        );
        List<AgreementPriceIndexVO> agreementPriceIndexVOList = this.setApplicableName(priceAgreementIndexPage.getRecords());

        agreementPriceIndexVOList.forEach(e -> {
            // 更新人名称脱敏处理
            e.setUpdateName(maskChineseName(e.getUpdateName()));
            // 针对价格值类型为空的情况，统一处理成返回 range
            if (StrUtil.isBlank(e.getPriceValueType())) {
                e.setPriceValueType(PriceValueTypeEnum.RANGE.getCode());
            }
        });
        Page<AgreementPriceIndexVO> agreementPriceIndexVOPage = new Page<>(priceAgreementIndexPage.getCurrent(), priceAgreementIndexPage.getSize(), priceAgreementIndexPage.getTotal());
        agreementPriceIndexVOPage.setRecords(agreementPriceIndexVOList);
        return PageResponse.buildSuccess(agreementPriceIndexVOPage);
    }

    @Override
    public MaterialAndVendorVO getMaterialAndVendorByMaterialType(String materialTypeCode){
        List<SupplyMaterial> materialList = materialService.listByMaterialTypeCode(materialTypeCode);
        List<Long> vendorIdList = agreementService.getValidAgreementVendorIdListByMaterialTypeCode(materialTypeCode);
        List<Vendor> vendorList = vendorService.listByIds(vendorIdList);
        return new MaterialAndVendorVO(materialList, vendorList);
    }
    @Override
    public AgreementPriceIndexEditVO getIndexByMaterialId(long materialId){
        SupplyMaterial material = materialService.getById(materialId);
        List<AgreementPriceIndex> agreementPriceIndexList = viewAgreementPriceIndexMapper.selectList(Wrappers.lambdaQuery(AgreementPriceIndex.class).eq(AgreementPriceIndex::getMaterialId, materialId));
        List<AgreementPriceIndexVO> agreementPriceIndexVOList = this.setApplicableName(agreementPriceIndexList);
        if (IterUtil.isNotEmpty(agreementPriceIndexVOList)) {
            agreementPriceIndexVOList.forEach(e -> {
                // 针对价格值类型为空的情况，统一处理成返回 range
                if (StrUtil.isBlank(e.getPriceValueType())) {
                    e.setPriceValueType(PriceValueTypeEnum.RANGE.getCode());
                }
            });
        }
        return new AgreementPriceIndexEditVO(material, agreementPriceIndexVOList);
    }

    public int removeByMaterialId(Long materialId) {
        return materialAgreementPriceMapper.delete(Wrappers.lambdaQuery(AgreementPrice.class).eq(AgreementPrice::getMaterialId, materialId));
    }

    @Override
    public int removeByMaterialIdList(List<Long> materialIdList) {
        return materialAgreementPriceMapper.delete(Wrappers.lambdaQuery(AgreementPrice.class).in(AgreementPrice::getMaterialId, materialIdList));
    }

    @Override
    public PriceExtEnumVO getPriceExtEnum() {
        List<UnitDTO> unitList = easyApiUtil.getUnit(AgreementPriceConstants.VALUE_SET_KEY_UNIT);
        List<UnitDTO> taxList = easyApiUtil.getUnit(AgreementPriceConstants.VALUE_SET_KEY_TAX);
        List<SupplyCity> cityList = dictService.getSupplyCity();
        unitList = unitList == null ? new ArrayList<>() : unitList;
        taxList = taxList == null ? new ArrayList<>() : taxList;
        return new PriceExtEnumVO(unitList, taxList, cityList);
    }

    /**
     * 根据企业 code 查询 name 并 set 到适用范围字段
     */
    private List<AgreementPriceIndexVO> setApplicableName(List<AgreementPriceIndex> agreementPriceIndexList){
        List<AgreementPriceIndexVO> agreementPriceIndexVOList = new ArrayList<>();
        if (CollectionUtil.isEmpty(agreementPriceIndexList)){
            return new ArrayList<>();
        }
        Map<String,String> entMap = extService.getEntMap();
        for (AgreementPriceIndex item : agreementPriceIndexList){
            AgreementPriceIndexVO agreementPriceIndexVO = new AgreementPriceIndexVO();
            BeanUtils.copyProperties(item, agreementPriceIndexVO);
            String entCode = item.getApplicableScopeCode();
            String entName = entMap.getOrDefault(entCode, "");
            agreementPriceIndexVO.setApplicableScopeName(entName);
            agreementPriceIndexVOList.add(agreementPriceIndexVO);
        }
        return agreementPriceIndexVOList;
    }

    @Override
    public Map<String, Object> minAndMaxAgreementPrice(Long materialId) {
        HashMap<String, Object> result = new HashMap<>(3);
        BigDecimal agreementPriceFrom;
        BigDecimal agreementPriceTo;
        LambdaQueryWrapper<AgreementPrice> wrapper = Wrappers.lambdaQuery(AgreementPrice.class)
                .select(AgreementPrice::getPriceValueType, AgreementPrice::getAgreementPriceFrom, AgreementPrice::getAgreementPriceTo)
                .eq(AgreementPrice::getMaterialId, materialId);
        List<AgreementPrice> agreementPrices = materialAgreementPriceMapper.selectList(wrapper);
        if (IterUtil.isEmpty(agreementPrices)) {
            result.put("type", PriceValueTypeEnum.RANGE.getCode());
            result.put("min", BigDecimal.ZERO);
            result.put("max", BigDecimal.ZERO);
            return result;
        }
        Set<String> distinctPriceValueTypes = agreementPrices.stream().map(e -> {
            String priceValueType = e.getPriceValueType();
            if (StrUtil.isNotBlank(priceValueType)) {
                return priceValueType;
            }
            return PriceValueTypeEnum.RANGE.getCode();
        }).collect(Collectors.toSet());
        // 只有1种值类型
        if (distinctPriceValueTypes.size() == 1) {
            Optional<String> first = distinctPriceValueTypes.stream().findFirst();
            String priceValueType = first.get();
            if (PriceValueTypeEnum.RANGE.getCode().equals(priceValueType)) {
                agreementPriceFrom = agreementPrices
                        .stream()
                        .min(Comparator.comparing(AgreementPrice::getAgreementPriceFrom))
                        .get()
                        .getAgreementPriceFrom();

                agreementPriceTo = agreementPrices
                        .stream()
                        .max(Comparator.comparing(AgreementPrice::getAgreementPriceTo))
                        .get()
                        .getAgreementPriceTo();
                result.put("type", PriceValueTypeEnum.RANGE.getCode());
                result.put("min", agreementPriceFrom);
                result.put("max", agreementPriceTo);
                return result;
            } else {
                int size = agreementPrices.size();
                BigDecimal sum = agreementPrices.stream().map(AgreementPrice::getAgreementPriceTo).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal agreementPrice = NumberUtil.div(sum, size, 2);
                result.put("type", PriceValueTypeEnum.FIXED.getCode());
                result.put("min", null);
                result.put("max", agreementPrice);
                return result;
            }
        }
        // 存在2种值类型
        else {
            // 分别过滤出固定值和范围值
            List<AgreementPrice> fixedPrice = agreementPrices
                    .stream()
                    .filter(e -> PriceValueTypeEnum.FIXED.getCode().equals(e.getPriceValueType()))
                    .collect(Collectors.toList());
            List<AgreementPrice> rangePrice = agreementPrices
                    .stream()
                    .filter(e -> StrUtil.isBlank(e.getPriceValueType()) || PriceValueTypeEnum.RANGE.getCode().equals(e.getPriceValueType()))
                    .collect(Collectors.toList());
            agreementPriceFrom = rangePrice
                    .stream()
                    .min(Comparator.comparing(AgreementPrice::getAgreementPriceFrom))
                    .get()
                    .getAgreementPriceFrom();

            agreementPriceTo = rangePrice
                    .stream()
                    .max(Comparator.comparing(AgreementPrice::getAgreementPriceTo))
                    .get()
                    .getAgreementPriceTo();
            for (AgreementPrice price : fixedPrice) {
                BigDecimal fixed = price.getAgreementPriceTo();
                if (fixed.compareTo(agreementPriceFrom) < 0) {
                    agreementPriceFrom = fixed;
                }
                if (fixed.compareTo(agreementPriceTo) > 0) {
                    agreementPriceTo = fixed;
                }
            }
            result.put("type", PriceValueTypeEnum.RANGE.getCode());
            result.put("min", agreementPriceFrom);
            result.put("max", agreementPriceTo);
            return result;
        }
    }

    @Override
    public List<SupplyVendorVO> getAgreementPriceVendor(Long materialId) {
        List<SupplyVendorVO> supplyVendors = null;
        Wrapper<AgreementPrice> wrapper = Wrappers.<AgreementPrice>lambdaQuery()
                .select(AgreementPrice::getVendorId)
                .eq(AgreementPrice::getMaterialId, materialId);
        List<AgreementPrice> agreementPrices = materialAgreementPriceMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(agreementPrices)){
            Set<Long> vendorIds = agreementPrices
                    .stream()
                    .map(AgreementPrice::getVendorId)
                    .collect(Collectors.toSet());
            supplyVendors = supplyVendorService.selectList(vendorIds);

        }
        return supplyVendors;
    }

    @Override
    public List<VendorAndAgreementVO> getVendorAndAgreementInfosByMaterialId(Long materialId) {
        return materialAgreementPriceMapper.getVendorAndAgreementInfosByMaterialId(materialId);
    }
}




