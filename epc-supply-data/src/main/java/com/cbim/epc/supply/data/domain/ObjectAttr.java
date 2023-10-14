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
public class ObjectAttr {

    /**
     * 对象id
     */
    private String objectId;

    /**
     * 对象属性版本
     */
    private String objVersion;

    /**
     * 属性分组
     */
    private List<MetaGroupInfo> groupList;

}
