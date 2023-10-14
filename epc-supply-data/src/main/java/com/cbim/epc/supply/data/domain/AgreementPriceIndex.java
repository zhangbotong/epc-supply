package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbim.epc.supply.data.handler.SupplyCityListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* 
* @TableName view_supply_material_agreement_price
*/
@TableName(value = "view_supply_material_agreement_price", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgreementPriceIndex implements Serializable {

    /**
     * 物料价格id
     */
    private Long priceId;

    /**
     * 物料id
     */
    private Long materialId;
    /**
    *
    * 物料编码
    */
    private String materialCode;
    /**
    *
    * 物料名称
    */
    private String materialName;
    /**
     * 物料类型编码
     */
    private String materialTypeCode;
    /**
     * 物料发布状态判断物料是否有效：0-无效；1-有效
     */
    private Integer materialPublishStatus;
    /**
    *
    * 供应商 id
    */
    private Long vendorId;
    /**
    *
    * 供应商名
    */
    private String vendorName;
    /**
    * 供应商统一社会信用代码
    */
    private String vendorUscc;
    /**
    *
    * 计量单位名称
    */
    private String measureUnitName;
    /**
     *
     * 计量单位编码
     */
    private String measureUnitCode;
    /**
     *
     * 计价单位名称
     */
    private String priceUnitName;
    /**
     *
     * 计价单位编码
     */
    private String priceUnitCode;
    /**
     * 集采协议价格值类型：fixed-固定值；range-范围值
     */
    private String priceValueType;
    /**
    *
    * 协议价（不含税）起
    */
    private BigDecimal agreementPriceFrom;
    /**
    *
    * 协议价（不含税）止
    */
    private BigDecimal agreementPriceTo;
    /**
    *
    * 税率
    */
    private BigDecimal taxRate;
    /**
     *
     * 税率编码
     */
    private String taxRateCode;
    /**
     *
     * 税率展示字段
     */
    private String taxRateDisplay;
    /**
    *
    * 供货城市
    */
    @TableField(typeHandler = SupplyCityListTypeHandler.class)
    private List<SupplyCity> supplyCity;
    /**
    *
    * 运输费
    */
    private BigDecimal shippingFee;
    /**
    *
    * 安装费
    */
    private BigDecimal installationFee;
    /**
    *
    * 其他费用
    */
    private BigDecimal otherFee;
    /**
    *
    * 可见性：0-隐藏，1-公开
    */
    private int visibility;
    /**
    *
    * 集采协议id
    */
    private Long agreementId;
    /**
    * 集采协议编码
    */
    private String agreementCode;
    /**
    *
    * 合约期限起始
    */
    private String contractPeriodBefore;
    /**
    *
    * 合约期限终止
    */
    private String contractPeriodAfter;
    /**
    * 适用范围编码
    */
    private String applicableScopeCode;
//    /**
//     * 适用范围名称
//     */
//    private String applicableScopeName;
    /**
     * 价格状态：有效、过期
     */
    private String calPriceStatus;
    /**
     * 集采表原始有效状态字段：release-发布，stop-停用，draft-草稿（删除一定是 stop 状态）
     */
    private String agreementState;
    /**
     * 处理后的集采是否有效状态字段：1-有效，0-无效
     */
    private Integer agreementCalValidStatus;
    /**
    *
    * 创建人名称
    */
    private String createName;
    /**
    *
    * 创建时间
    */
    private LocalDateTime createDate;
    /**
    *
    * 更新人名称
    */
    private String updateName;
    /**
    *
    * 更新时间
    */
    private LocalDateTime updateDate;
}
