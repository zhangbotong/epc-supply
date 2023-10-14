package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 供应资源管理-集采协议主表
* @TableName view_agreement_state_release
*/
@TableName(value = "view_supply_agreement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agreement implements Serializable {

    /**
    *
    * 主键-使用雪花算法
    */
    private Long id;
    /**
     *
     * 集采协议名称
     */
    private String name;
    /**
     *
     * 集采协议编码
     */
    private String code;
    /**
    *
    * 供应商Id
    */
    private Long vendorId;
    /**
     * 适用范围编码(对应集采企业编码:entCode)
     */
    private String applicableScopeCode;
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
     * 集采协议状态：有效、过期（实时计算）
     */
    private String calPriceStatus;
    /**
     * 格式化有效期：contractPeriodBefore~contractPeriodAfter
     */
    private String period;
    /**
     * 集采表原始状态字段：release-发布，stop-停用，draft-草稿（删除一定是 stop 状态）
     */
    private String state;
    /**
     * 处理后的是否有效状态字段：1-有效，0-无效
     */
    private Integer calValidStatus;
}
