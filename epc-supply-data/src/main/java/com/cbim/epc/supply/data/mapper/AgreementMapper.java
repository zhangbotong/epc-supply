package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import com.cbim.epc.supply.data.domain.Agreement;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author kyrie
* @description 针对表【view_agreement_state_release(供应资源管理-集采协议主表)】的数据库操作Mapper
* @createDate 2023-05-19 14:10:40
* @Entity com.cbim.epc.supply.data.domain.EpcSupplyAgreement
*/
public interface AgreementMapper extends BaseMapperX<Agreement> {

    @Select("select a.* from epc_supply_agreement_material am join view_supply_agreement a on am.agreement_id = a.id where am.material_type_id = #{materialTypeId} and a.cal_valid_status = 1")
    List<Agreement> selectValidByMaterialTypeId(Long materialTypeId);
}




