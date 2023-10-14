package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商物料分类表
 * @TableName epc_supply_vendor_material_category
 */
@TableName(value ="epc_supply_vendor_material_category")
@Data
public class SupplyVendorMaterialCategory implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long vendorId;

    /**
     * 
     */
    private Long materialCategoryId;

    /**
     * 物料分类code
     */
    private String materialCategoryCode;

    /**
     * 
     */
    private Date createDate;

    /**
     * 
     */
    private Long createBy;

    /**
     * 
     */
    private Date updateDate;

    /**
     * 
     */
    private Long updateBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}