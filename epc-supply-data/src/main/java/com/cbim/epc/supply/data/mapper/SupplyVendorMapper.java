package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import com.cbim.epc.supply.data.domain.SupplyVendor;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
* @author libiao
* @description 针对表【epc_supply_vendor(供应商)】的数据库操作Mapper
* @createDate 2023-05-18 17:07:58
* @Entity com/cbim.epc.supply.data.domain.SupplyVendor
*/
public interface SupplyVendorMapper extends BaseMapperX<SupplyVendor> {

    /**
     * 查询供应商详细信息列表
     * @param vendorIds 供应商id列表
     * @return 供应商详情列表
     */
    List<SupplyVendorVO> selectList(@Param(value = "vendorIds") Collection<Long> vendorIds);

    /**
     * 查询供应商详细信息列表
     * @param usccSet 供应商编码列表
     * @return 供应商详情列表
     */
    List<SupplyVendorVO> selectByUscc(@Param(value = "usccSet") Collection<String> usccSet);
}




