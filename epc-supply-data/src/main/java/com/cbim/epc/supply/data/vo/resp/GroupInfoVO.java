package com.cbim.epc.supply.data.vo.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GroupInfoVO {

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
    private List<SupplyMaterialAttributeVO> attrList;
}