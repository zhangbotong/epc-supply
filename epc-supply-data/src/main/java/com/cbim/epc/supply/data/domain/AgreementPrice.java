package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbim.epc.supply.common.mybatis.dataobject.BaseDO;
import com.cbim.epc.supply.data.handler.SupplyCityListTypeHandler;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应资源管理-材料价格-集采协议表
 * @TableName epc_supply_material_agreement_price
 */
@TableName(value ="epc_supply_material_agreement_price", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgreementPrice extends BaseDO implements Serializable {

    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 物料id
     */
    @NotNull(message = "Material id cannot be null")
    private Long materialId;

    /**
     * 供应商id（应该与物料表保持一致，若物料的供应商id会发生变化，这里就不存）
     */
    @NotNull(message = "Vendor id cannot be null")
    private Long vendorId;

    /**
     * 集采协议id
     */
    @NotNull(message = "Agreement id cannot be null")
    private Long agreementId;

    /**
     * 计量单位名称
     */
    @NotNull(message = "Measure unit name cannot be null")
    private String measureUnitName;

    /**
     * 计量单位编码
     */
    @NotNull(message = "Measure unit code cannot be null")
    private String measureUnitCode;

    /**
     * 计价单位名称
     */
    @NotNull(message = "Price unit name cannot be null")
    private String priceUnitName;

    /**
     * 计价单位编码
     */
    @NotNull(message = "Price unit code cannot be null")
    private String priceUnitCode;

    /**
     * 集采协议价格值类型：fixed-固定值；range-范围值
     */
    @NotBlank(message = "Price Value Type cannot be blank")
    private String priceValueType;

    /**
     * 协议价（不含税）起
     */
    @NotNull(message = "Agreement price-from cannot be null")
    private BigDecimal agreementPriceFrom;

    /**
     * 协议价（不含税）止
     */
    @NotNull(message = "Agreement price-to cannot be null")
    private BigDecimal agreementPriceTo;

    /**
     * 税率
     */
    @NotNull(message = "Tax rate cannot be null")
    private BigDecimal taxRate;
    /**
     * 税率编码（值集）
     */
    @NotNull(message = "Tax rate-code cannot be null")
    private String taxRateCode;
    /**
     * 税率展示字段
     */
    @NotNull(message = "Tax rate-display cannot be null")
    private String taxRateDisplay;

    /**
     * 供货城市及费用信息
     */
    @NotEmpty(message = "Supply city cannot be empty")
    @TableField(typeHandler = SupplyCityListTypeHandler.class)
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
     * 其他费用
     */
    private BigDecimal otherFee;
    /**
     * 可见性：0-隐藏，1-可见
     */
    @NotNull(message = "Visibility cannot be null")
    private Integer visibility;
    /**
     * 发布状态：0-草稿；1-已发布
     */
    private Integer publishStatus = 1;

    /**
     * 0-没有发布过；1-发布过
     */
    private Integer hasBeenPublished;

    /**
     * 可见性枚举类
     */
    @Getter
    @AllArgsConstructor
    public enum VisibilityEnum {
        HIDDEN(0, "隐藏"),
        VISIBLE(1, "可见");

        @JsonValue
        private Integer value;
        private String name;

        /**
         * 获取所有name list
         */
        public static List<String> getAllName() {
            List<String> nameList = new ArrayList<>();
            for (VisibilityEnum visibilityEnum : VisibilityEnum.values()) {
                nameList.add(visibilityEnum.getName());
            }
            return nameList;
        }

        public static VisibilityEnum getByValue(Integer value) {
            for (VisibilityEnum visibilityEnum : VisibilityEnum.values()) {
                if (visibilityEnum.getValue().equals(value)) {
                    return visibilityEnum;
                }
            }
            return null;
        }
    }
}