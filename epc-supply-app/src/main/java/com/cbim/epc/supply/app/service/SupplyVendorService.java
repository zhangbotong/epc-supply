package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.common.base.service.BaseService;
import com.cbim.epc.supply.data.domain.SupplyVendor;
import com.cbim.epc.supply.data.vo.resp.MaterialPriceContractVendorVO;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author 123
 */
public interface SupplyVendorService extends BaseService<SupplyVendor> {

    List<MaterialPriceContractVendorVO> getMaterialVendor(String objTypeCode);

    /**
     * 查询供应商详情
     * @param vendorIds 供应商id
     * @return List<SupplyVendorVO> 供应商详情列表
     */
    List<SupplyVendorVO> selectList(Collection<Long> vendorIds);

    /**
     * 查询供应商详情
     * @param usccSet 供应商编码
     * @return List<SupplyVendorVO> 供应商详情列表
     */
    List<SupplyVendorVO> selectByUscc(Set<String> usccSet);
}
