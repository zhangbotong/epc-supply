package com.cbim.epc.supply.app.service.impl;

import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.domain.SupplyAgreement;
import com.cbim.epc.supply.app.service.SupplyAgreementService;
import com.cbim.epc.supply.data.mapper.SupplyAgreementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author kyrie
* @description 针对表【epc_supply_agreement(供应资源管理-集采协议主表)】的数据库操作Service实现
* @createDate 2023-05-19 14:10:40
*/
@Service
@RequiredArgsConstructor
public class SupplyAgreementServiceImpl extends BaseServiceImpl<SupplyAgreementMapper, SupplyAgreement>
    implements SupplyAgreementService {

    private final SupplyAgreementMapper supplyAgreementMapper;
    @Override
    public List<SupplyAgreement> selectByMaterialTypeId(Long materialTypeId) {
        List<SupplyAgreement> supplyAgreementList = supplyAgreementMapper.selectByMaterialTypeId(materialTypeId);
        return supplyAgreementList == null ? new ArrayList<>() : supplyAgreementList;
    }
}




