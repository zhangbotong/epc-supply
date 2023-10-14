package com.cbim.epc.supply.app.service;

import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.supply.common.base.service.BaseService;
import com.cbim.epc.supply.data.domain.AgreementPrice;
import com.cbim.epc.supply.data.vo.req.MaterialPriceQueryParam;
import com.cbim.epc.supply.data.vo.resp.*;

import java.util.List;
import java.util.Map;

/**
* @author kyrie
* @description 针对表【epc_supply_material_agreement_price(供应资源管理-材料价格-集采协议表)】的数据库操作Service
*/
public interface AgreementPriceService extends BaseService<AgreementPrice> {

    /**
     * 整个物料维度的价格更新，先根据物料删除，再全部新增
     */
    int saveOrUpdatePrice(List<AgreementPrice> agreementPriceList);

    PageResponse<AgreementPriceIndexVO> search(MaterialPriceQueryParam priceQueryParam);

    /**
     * 根据物料类型获取物料和供应商
     * 物料类型编码 --> 物料
     * 物料类型 id --> 集采协议 --> 供应商
     * 应用场景：集采协议价点击新增按钮
     */
    MaterialAndVendorVO getMaterialAndVendorByMaterialType(String materialTypeCode);

    /**
     * 根据物料id获取集采协议价
     * 应用场景：点击编辑按钮，获取该物料下的所有集采协议价
     */
    AgreementPriceIndexEditVO getIndexByMaterialId(long materialId);

    int removeByMaterialIdList(List<Long> materialIdList);

    /**
     * 值集接口
     */
    PriceExtEnumVO getPriceExtEnum();

    /**
     * 获取最大、最小的集采协议价
     * @param materialId 物料id
     * @return /
     */
    Map<String, Object> minAndMaxAgreementPrice(Long materialId);

    /**
     * 获取集采协议价的供应商
     * @param materialId 物料id
     * @return List<SupplyVendorVO> 供应商列表
     */
    List<SupplyVendorVO> getAgreementPriceVendor(Long materialId);

    /**
     * 通过物料id查询相关供应商以及集采协议相关的信息
     *
     * @param materialId 物料id
     * @return /
     */
    List<VendorAndAgreementVO> getVendorAndAgreementInfosByMaterialId(Long materialId);
}