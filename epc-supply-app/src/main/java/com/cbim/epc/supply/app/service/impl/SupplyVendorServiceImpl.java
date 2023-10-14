package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.UserInfoDTO;
import com.cbim.epc.supply.app.service.SupplyVendorService;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.domain.SupplyVendor;
import com.cbim.epc.supply.data.domain.SupplyVendorMaterialCategory;
import com.cbim.epc.supply.data.mapper.SupplyVendorMapper;
import com.cbim.epc.supply.data.mapper.SupplyVendorMaterialCategoryMapper;
import com.cbim.epc.supply.data.vo.resp.MaterialPriceContractVendorVO;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplyVendorServiceImpl extends BaseServiceImpl<SupplyVendorMapper, SupplyVendor> implements SupplyVendorService {

    private final SupplyVendorMapper vendorMapper;

    private final SupplyVendorMaterialCategoryMapper materialCategoryMapper;

    private final EasyApiUtil easyApiUtil;

    @Override
    public List<MaterialPriceContractVendorVO> getMaterialVendor(String objTypeCode) {
        List<SupplyVendorMaterialCategory> materialCategories = materialCategoryMapper.selectList(Wrappers.lambdaQuery(SupplyVendorMaterialCategory.class)
                .eq(SupplyVendorMaterialCategory::getMaterialCategoryCode, objTypeCode));
        if (CollUtil.isEmpty(materialCategories)) {
            return new ArrayList<>();
        }
        List<Long> idList = materialCategories.stream().map(SupplyVendorMaterialCategory::getVendorId).collect(Collectors.toList());
        List<SupplyVendor> supplyVendors = vendorMapper.selectList(Wrappers.lambdaQuery(SupplyVendor.class)
                .in(SupplyVendor::getId, idList));
        return supplyVendors.stream().map(v -> {
            MaterialPriceContractVendorVO supplyVendorVO = new MaterialPriceContractVendorVO();
            BeanUtils.copyProperties(v, supplyVendorVO);
            return supplyVendorVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SupplyVendorVO> selectList(Collection<Long> vendorId) {
        List<SupplyVendorVO> supplyVendors = vendorMapper.selectList(vendorId);
        if (CollectionUtil.isNotEmpty(supplyVendors)) {
            List<String> userIds = supplyVendors
                    .stream()
                    .map(SupplyVendorVO::getUserId)
                    .collect(Collectors.toList());
            List<UserInfoDTO> users = easyApiUtil.getUsersByIds(userIds);
            Map<String, UserInfoDTO> idMapping = users
                    .stream()
                    .collect(Collectors.toMap(UserInfoDTO::getId, Function.identity()));
            for (SupplyVendorVO supplyVendor : supplyVendors) {
                Optional.ofNullable(idMapping.get(supplyVendor.getUserId()))
                        .ifPresent(user -> {
                            supplyVendor.setContact(user.getMobile());
                            supplyVendor.setUserName(user.getName());
                        });
            }
        }
        return supplyVendors;
    }

    @Override
    public List<SupplyVendorVO> selectByUscc(Set<String> usccSet) {
        List<SupplyVendorVO> supplyVendors = null;
        if (CollectionUtil.isNotEmpty(usccSet)) {
            supplyVendors = vendorMapper.selectByUscc(usccSet);
            if (CollectionUtil.isNotEmpty(supplyVendors)) {
                List<String> userIds = supplyVendors
                        .stream()
                        .map(SupplyVendorVO::getUserId)
                        .collect(Collectors.toList());
                List<UserInfoDTO> users = easyApiUtil.getUsersByIds(userIds);
                Map<String, UserInfoDTO> idMapping = CollectionUtil.emptyIfNull(users)
                        .stream()
                        .collect(Collectors.toMap(UserInfoDTO::getId, Function.identity()));
                for (SupplyVendorVO supplyVendor : supplyVendors) {
                    supplyVendor.setContact(idMapping.get(supplyVendor.getUserId()).getMobile());
                    supplyVendor.setUserName(idMapping.get(supplyVendor.getUserId()).getName());
                }
            }
        }
        return supplyVendors;
    }
}
