package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
* 供应资源管理-集采协议-物料分类 关联关系表
* @TableName epc_supply_agreement_material
*/
@TableName(value = "epc_supply_agreement_material")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyAgreementMaterial implements Serializable {

    /**
    *
    * 主键-使用雪花算法
    */
    private Long id;
    /**
    *
    * 集采协议Id
    */
    private Long agreementId;
    /**
    *
    * 物料分类Id
    */
    private Long materialTypeId;
}
