package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cbim.epc.supply.data.handler.SupplyCityListTypeHandler;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@TableName(value = "epc_supply_material_temporary_price", autoResultMap = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPriceTemporary {

    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String createName;

    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 更新人名称
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updateName;


}
