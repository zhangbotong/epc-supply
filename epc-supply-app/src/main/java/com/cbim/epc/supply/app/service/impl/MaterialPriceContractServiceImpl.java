package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.supply.app.service.MaterialPriceContractService;
import com.cbim.epc.supply.app.service.SupplyVendorService;
import com.cbim.epc.supply.common.base.exception.EpcSupplyException;
import com.cbim.epc.supply.common.enums.ValidEnum;
import com.cbim.epc.supply.common.utils.BinaryConverterUtil;
import com.cbim.epc.supply.data.domain.Contract;
import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.mapper.ContractMapper;
import com.cbim.epc.supply.data.mapper.MaterialPriceContractMapper;
import com.cbim.epc.supply.data.mapper.SupplyMaterialMapper;
import com.cbim.epc.supply.data.vo.req.MaterialPriceQueryParam;
import com.cbim.epc.supply.data.vo.resp.ContractPriceAndOrderVO;
import com.cbim.epc.supply.data.vo.resp.ContractPriceDetailVO;
import com.cbim.epc.supply.data.vo.resp.ContractPriceVO;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kyrie
 * @description 针对表【epc_supply_material_price_contract(供应资源管理-材料价格-历史合同表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class MaterialPriceContractServiceImpl implements MaterialPriceContractService {

    private final MaterialPriceContractMapper priceContractMapper;

    private final SupplyMaterialMapper materialMapper;

    private final ContractMapper contractMapper;

    private final SupplyVendorService supplyVendorService;

    @Override
    public Page<ContractPriceAndOrderVO> getContractPricePageList(MaterialPriceQueryParam queryParam) {
        Page<ContractPriceAndOrderVO> orderVOPage = new Page<>();
        Page<ContractPriceVO> contractPricePage = getContractPricePage(queryParam);
        List<ContractPriceVO> records = contractPricePage.getRecords();
        if (CollUtil.isEmpty(records)) {
            return orderVOPage;
        }
        List<ContractPriceAndOrderVO> priceAndOrderVOS = getContractPriceAndOrderList(records);
        orderVOPage.setRecords(priceAndOrderVOS);
        orderVOPage.setTotal(contractPricePage.getTotal());
        orderVOPage.setSize(contractPricePage.getSize());
        orderVOPage.setCurrent(contractPricePage.getCurrent());
        return orderVOPage;
    }

    @NotNull
    private List<ContractPriceAndOrderVO> getContractPriceAndOrderList(List<ContractPriceVO> records) {
        List<ContractPriceAndOrderVO> priceAndOrderList = new ArrayList<>();
        Map<Long, List<ContractPriceVO>> listMap = records.stream()
                .collect(Collectors.groupingBy(ContractPriceVO::getMaterialId));
        listMap.forEach((k, v) -> {
            v.forEach(c -> {
                if (c.getPublishStatus() == 0) {
                    c.setContractStatus(0);
                } else {
                    Contract contract = contractMapper.selectById(c.getContractId());
                    c.setContractStatus(ObjUtil.isNotEmpty(contract) && ObjUtil.equals(contract.getIsValid(), ValidEnum.VALID.getCode()) ? 1 : 0);
                }
            });
            v = v.stream().sorted(Comparator.comparing(ContractPriceVO::getContractStatus).reversed()).collect(Collectors.toList());
            ContractPriceAndOrderVO priceAndOrder = ContractPriceAndOrderVO.builder()
                    .materialCode(v.get(0).getMaterialCode())
                    .materialName(v.get(0).getMaterialName())
                    .objectTypeCode(v.get(0).getObjectTypeCode())
                    .materialId(k)
                    .materialPublishStatus(v.get(0).getPublishStatus())
                    .contractPriceList(v)
                    .build();
            priceAndOrderList.add(priceAndOrder);
        });
        return priceAndOrderList.stream().sorted(
                Comparator.comparingInt(ContractPriceAndOrderVO::getMaterialPublishStatus).reversed()
        ).collect(Collectors.toList());
    }

    private Page<ContractPriceVO> getContractPricePage(MaterialPriceQueryParam queryParam) {
        String nameOrCode = queryParam.getMaterialNameOrCode();
        MPJLambdaWrapper<MaterialContractPrice> wrapper = JoinWrappers.lambda(MaterialContractPrice.class)
                .select(SupplyMaterial::getMaterialCode, SupplyMaterial::getMaterialName, SupplyMaterial::getObjectTypeCode, SupplyMaterial::getId, SupplyMaterial::getPublishStatus, SupplyMaterial::getIsValid)
                .selectAll(MaterialContractPrice.class)
                .innerJoin(SupplyMaterial.class, SupplyMaterial::getId, MaterialContractPrice::getMaterialId)
                .eq(MaterialContractPrice::getIsValid, ValidEnum.VALID.getCode())
                .likeRight(SupplyMaterial::getObjectTypeCode, queryParam.getObjectTypeCode());
        if (StrUtil.isNotBlank(nameOrCode)) {
            wrapper.and(qr -> qr.like(SupplyMaterial::getMaterialCode, nameOrCode)
                    .or()
                    .like(SupplyMaterial::getMaterialName, nameOrCode));
        }
        return priceContractMapper.selectJoinPage(new Page<>(queryParam.getPageNum(), queryParam.getPageSize()),
                ContractPriceVO.class, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdatePrice(List<MaterialContractPrice> contractPriceList) {
        if (CollectionUtil.isEmpty(contractPriceList)) {
            throw new EpcSupplyException("价格信息不能为空");
        }
        MaterialContractPrice contractPrice = contractPriceList.get(0);
        Long materialId = contractPrice.getMaterialId();
        Optional.ofNullable(materialId).orElseThrow(() -> new EpcSupplyException("物料id不能为空"));
        SupplyMaterial supplyMaterial = materialMapper.selectById(materialId);
        Optional.ofNullable(supplyMaterial).orElseThrow(() -> new EpcSupplyException("物料不存在"));

        priceContractMapper.delete(Wrappers.lambdaQuery(MaterialContractPrice.class)
                .eq(MaterialContractPrice::getMaterialId, materialId));
        priceContractMapper.insertBatch(contractPriceList);

        int subData = supplyMaterial.getSubData();
        int changed = BinaryConverterUtil.changeBitValue(subData, 2, '1');
        if (changed >= 0) {
            materialMapper.updateById(SupplyMaterial.builder()
                    .id(materialId)
                    .subData(changed)
                    .build());
        }
        return materialId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByMaterialIdList(List<Long> materialIdList) {
        priceContractMapper.delete(Wrappers.lambdaQuery(MaterialContractPrice.class)
                .in(MaterialContractPrice::getMaterialId, materialIdList));
        List<SupplyMaterial> supplyMaterials = materialMapper.selectList(Wrappers.lambdaQuery(SupplyMaterial.class)
                .in(SupplyMaterial::getId, materialIdList));
        List<SupplyMaterial> materialList = supplyMaterials.stream().map(m -> {
            int subData = m.getSubData();
            int changed = BinaryConverterUtil.changeBitValue(subData, 2, '0');
            if (changed >= 0) {
                return SupplyMaterial.builder()
                        .subData(changed)
                        .id(m.getId())
                        .build();
            }
            return SupplyMaterial.builder()
                    .subData(subData)
                    .id(m.getId())
                    .build();
        }).collect(Collectors.toList());
        materialMapper.updateBatch(materialList, materialList.size());
    }

    @Override
    public ContractPriceDetailVO selectById(String id) {
        SupplyMaterial supplyMaterial = materialMapper.selectById(id);
        List<MaterialContractPrice> contractPrices = priceContractMapper
                .selectList(Wrappers.lambdaQuery(MaterialContractPrice.class)
                        .eq(MaterialContractPrice::getMaterialId, id));
        return ContractPriceDetailVO.builder()
                .material(supplyMaterial)
                .contractPriceList(contractPrices)
                .build();
    }

    @Override
    public BigDecimal contractPriceAverage(Long materialId) {
        BigDecimal contractPriceAverage = null;
        Wrapper<MaterialContractPrice> wrapper = Wrappers.<MaterialContractPrice>lambdaQuery()
                .select(MaterialContractPrice::getUnitPrice)
                .eq(MaterialContractPrice::getMaterialId, materialId);
        List<MaterialContractPrice> priceContracts = priceContractMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(priceContracts)) {
            contractPriceAverage = priceContracts
                    .stream()
                    .map(MaterialContractPrice::getUnitPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(priceContracts.size()), 2, BigDecimal.ROUND_HALF_UP);
        }
        return contractPriceAverage;
    }

    @Override
    public List<SupplyVendorVO> getContractPriceVendor(Long materialId) {
        List<SupplyVendorVO> supplyVendors = null;
        Wrapper<MaterialContractPrice> wrapper = Wrappers.<MaterialContractPrice>lambdaQuery()
                .select(MaterialContractPrice::getVendorUscc)
                .eq(MaterialContractPrice::getMaterialId, materialId);
        List<MaterialContractPrice> contractPrices = priceContractMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(contractPrices)) {
            Set<String> usccSet = contractPrices
                    .stream()
                    .filter(e -> !"uscc".equals(e.getVendorUscc()))
                    .flatMap(e -> Arrays.stream(e.getVendorUscc().split(",")))
                    .collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(usccSet)) {
                supplyVendors = supplyVendorService.selectByUscc(usccSet);
            }
        }
        return supplyVendors;
    }
}




