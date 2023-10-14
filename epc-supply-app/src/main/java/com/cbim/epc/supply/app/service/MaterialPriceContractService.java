package com.cbim.epc.supply.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import com.cbim.epc.supply.data.vo.req.MaterialPriceQueryParam;
import com.cbim.epc.supply.data.vo.resp.ContractPriceAndOrderVO;
import com.cbim.epc.supply.data.vo.resp.ContractPriceDetailVO;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;

import java.math.BigDecimal;
import java.util.List;

/**
* @author kyrie
* @description 针对表【epc_supply_material_price_contract(供应资源管理-材料价格-历史合同表)】的数据库操作Service
*/
public interface MaterialPriceContractService  {

    Page<ContractPriceAndOrderVO> getContractPricePageList(MaterialPriceQueryParam queryParam);

    Long saveOrUpdatePrice(List<MaterialContractPrice> contractPriceList);

    void removeByMaterialIdList(List<Long> materialIdList);

    ContractPriceDetailVO selectById(String id);
    /**
     * 查询历史合同平均价
     * @param materialId 物料id
     * @return java.math.BigDecimal 历史合同平均价
     */
    BigDecimal contractPriceAverage(Long materialId);


    /**
     * 获取历史合同价的供应商
     * @param materialId 物料id
     * @return List<SupplyVendorVO> 供应商列表
     */
    List<SupplyVendorVO> getContractPriceVendor(Long materialId);
}
