package com.cbim.epc.supply.data.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GroupInfo {

    /**
     * 分组code
     */
    private Long groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 属性信息
     */
    private List<SupplyMaterialAttribute> attrList;
}
