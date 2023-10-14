package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import com.cbim.epc.supply.data.domain.AgreementPrice;
import com.cbim.epc.supply.data.vo.resp.VendorAndAgreementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* @author kyrie
* @description 针对表【epc_supply_material_agreement_price(供应资源管理-材料价格-集采协议表)】的数据库操作Mapper
* @createDate 2023-05-06 10:10:18
*/
public interface MaterialAgreementPriceMapper extends BaseMapperX<AgreementPrice> {

    List<VendorAndAgreementVO> getVendorAndAgreementInfosByMaterialId(@Param("materialId") Long materialId);

    Integer getMaterialCategoryAgreementPriceCount(@Param("materialCategoryCode") String materialCategoryCode);

}




