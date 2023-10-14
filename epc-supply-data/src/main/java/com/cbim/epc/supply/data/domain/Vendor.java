package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
* 
* @TableName view_supply_vendor
*/
@TableName(value = "view_supply_vendor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor implements Serializable {

    /**
    *
    * 主键-使用雪花算法
    */
    private Long id;
    /**
    *
    * 供应商名
    */
    private String name;
    /**
    *
    * 统一社会信用代码(含加密)
    */
    private String uscc;
    /**
     * 状态："1"-有效
     */
    private String state;
}
