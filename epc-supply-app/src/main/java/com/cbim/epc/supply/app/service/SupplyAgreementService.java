package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.data.domain.SupplyAgreement;
import com.cbim.epc.supply.common.base.service.BaseService;

import java.util.List;

/**
* @author kyrie
* @description 针对表【epc_supply_agreement(供应资源管理-集采协议主表)】的数据库操作Service
* @createDate 2023-05-19 14:10:40
*/
public interface SupplyAgreementService extends BaseService<SupplyAgreement> {

    /**
    * @description 根据物料类型id查询集采协议
    * @param materialTypeId 物料类型id
    * @return java.util.List<com.cbim.epc.supply.data.domain.SupplyAgreement>
    */
    List<SupplyAgreement> selectByMaterialTypeId(Long materialTypeId);
}
