package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 供应资源管理-集采协议主表
* @TableName epc_supply_agreement
*/
@TableName(value = "epc_supply_agreement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyAgreement implements Serializable {

    /**
    *
    * 主键-使用雪花算法
    */
    private Long id;
    /**
    *
    * 供应商Id
    */
    private Long supplierId;
    /**
    *
    * 企业编码
    */
    private String entCode;
    /**
    *
    * 合约期限
    */
    private String contractPeriodBefore;
    /**
    *
    * 协议基本信息
    */
    private Object contractInfo;
    /**
    *
    * 上传文件结构
    */
    private Object file;
    /**
    *
    * 创建时间
    */
    private LocalDateTime createDate;
    /**
    *
    * 更新时间
    */
    private LocalDateTime updateDate;
    /**
    *
    * 创建人id
    */
    private String createBy;
    /**
    *
    * 创建人名称
    */
    private String createName;
    /**
    *
    * 更新人id
    */
    private String updateBy;
    /**
    *
    * 更新人名称
    */
    private String updateName;
    /**
    *
    * 合约期限
    */
    private String contractPeriodAfter;
    /**
    *
    * darft release stop
    */
    private String state;
    /**
    *
    * 名称
    */
    private String name;
    /**
    *
    * 编码
    */
    private String code;
    /**
    *
    * 是否删除 'yse' 'no'
    */
    private String isDeleted;
}
