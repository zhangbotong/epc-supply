package com.cbim.epc.supply.data.vo.req;

import com.cbim.epc.supply.data.domain.AgreementPrice;
import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import lombok.Data;

import java.util.List;

/**
 * 材料价格聚合对象
 */
@Data
public class MaterialPriceExtReq{

    private List<AgreementPrice> priceAgreementList;

    private List<MaterialContractPrice> priceContractList;

    private Long id;

    /**
     * 适用范围名称
     */
    private String applicableScopeName;

    /**
     * 适用范围编码
     */
    private String applicableScopeCode;

    /**
     * 价格version
     */
    private String version;

    /**
     * 物料id
     */
    private Long materialId;

    /**
     * 产品备注说明
     */
    private String productDescription;

    /**
     * 发布状态：0-草稿；1-已发布
     */
    private Integer publishStatus;

    /**
     * 0-没有发布过；1-发布过
     */
    private Integer hasBeenPublished;

    /**
     * 被引用次数
     */
    private Long referenceCount;

    /**
     * 子表数据：
     *  0-集采协议和历史合同都不存在；
     *  1-存在集采协议；
     *  2-存在历史合同；
     *  3-集采协议和历史合同都存在
     */
    private Integer subData;

}
