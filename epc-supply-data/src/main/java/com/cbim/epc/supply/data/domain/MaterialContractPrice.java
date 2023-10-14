package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbim.epc.supply.common.mybatis.dataobject.BaseDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应资源管理-材料价格-历史合同表
 */
@TableName(value ="epc_supply_material_contract_price")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialContractPrice extends BaseDO implements Serializable {
    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 物料id
     */
    private Long materialId;


    private String vendorName;

    private String vendorUscc;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 合同编号
     */
    private String contractCode;

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
     * 合同单价
     */
    private BigDecimal unitPrice;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 工程量
     */
    private BigDecimal engineeringQuantity;

    /**
     * 合同总价
     */
    private BigDecimal totalPrice;


    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createdDateTime;

    private String projectName;

    private String buildSite;

    private String applicableScopeName;

    private String applicableScopeCode;

    /**
     * 费项备注
     */
    private String expenseNotes;

    /**
     * 可见性：0-隐藏，1-可见
     */
    private int visibility;
}