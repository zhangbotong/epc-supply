package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import com.cbim.epc.supply.data.domain.SupplyAgreement;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author kyrie
* @description 针对表【epc_supply_agreement(供应资源管理-集采协议主表)】的数据库操作Mapper
* @createDate 2023-05-19 14:10:40
* @Entity com.cbim.epc.supply.data.domain.EpcSupplyAgreement
*/
public interface SupplyAgreementMapper extends BaseMapperX<SupplyAgreement> {

    @Select("select a.* from epc_supply_agreement_material am join epc_supply_agreement a on am.agreement_id = a.id where am.material_type_id = 1656946744756498432")
    List<SupplyAgreement> selectByMaterialTypeId(Long materialTypeId);
}




