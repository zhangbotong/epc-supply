package com.cbim.epc.supply.data.vo.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MaterialPriceTemporarySearchVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料id
     */
    private Long materialId;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料分类编码
     */
    private String objectTypeCode;

    /**
     * 该物料在各供应商下的临时报价列表
     */
    private List<TemporaryInfo> temporaryInfoList;

    @Data
    public static class TemporaryInfo {
        /**
         * 供应商id
         */
        private Long vendorId;

        /**
         * 供应商名称
         */
        private String vendorName;

        /**
         * 统一社会信用代码
         */
        private String usci;

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
        private Object supplyCity;

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
         * 更新人id
         */
        private Long updateBy;

        /**
         * 更新人名称
         */
        private String updateName;

        /**
         * 最后更新时间
         */
        private LocalDateTime updateDate;

    }
}
