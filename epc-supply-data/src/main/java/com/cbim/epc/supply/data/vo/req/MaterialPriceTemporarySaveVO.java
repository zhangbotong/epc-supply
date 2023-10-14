package com.cbim.epc.supply.data.vo.req;

import com.cbim.epc.supply.data.domain.SupplyCity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 临时报价保存VO
 *
 * @author xiaozp
 * @since 2023/5/30
 */
@Data
public class MaterialPriceTemporarySaveVO {

    /**
     * 物料id
     */
    private Long materialId;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 计量单位名称
     */
    private String measureUnitName;

    /**
     * 计量单位编码
     */
    private String measureUnitCode;

    /**
     * 计价单位名称
     */
    private String priceUnitName;

    /**
     * 计价单位编码
     */
    private String priceUnitCode;

    /**
     * 临时报价（不含税）
     */
    private BigDecimal tempPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 供货城市
     */
    private List<SupplyCity> supplyCity;

    /**
     * 运输费
     */
    private BigDecimal shippingFee;

    /**
     * 安装费
     */
    private BigDecimal installationFee;

    /**
     * 备注
     */
    private String notes;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 适用范围名称
     */
    private String applicableScopeName;

    /**
     * 适用范围编码
     */
    private String applicableScopeCode;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateDate;

    /**
     * 创建人id
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 更新人id
     */
    private Long updateBy;

    /**
     * 更新人名称
     */
    private String updateName;
}
