package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商
 * @TableName epc_supply_vendor
 */
@TableName(value ="epc_supply_vendor")
@Data
public class SupplyVendor implements Serializable {
    /**
     * 主键-使用雪花算法
     */
    @TableId
    private Long id;

    /**
     * 供应商名
     */
    private String name;

    /**
     * 统一社会信用代码查询
     */
    private String uscc;

    /**
     * 企业logo
     */
    private String logo;

    /**
     * 法人
     */
    @TableField(value = "juridical_person")
    private String juridicalPerson;

    /**
     * 公司地址
     */
    @TableField(value = "company_address")
    private String companyAddress;

    /**
     * 数据来源 状态
     */
    private String source;

    /**
     * 供应商等级：项目临时供应商:pro_temporary，企业供应商:ent_grade
     */
    @TableField(value = "vendor_level")
    private String vendorLevel;

    /**
     * 状态
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人id
     */
    private Long createBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 更新人id
     */
    private Long updateBy;

    /**
     * 供应商等级
     */
//    private String supplierGrade;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}