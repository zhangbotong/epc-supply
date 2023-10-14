package com.cbim.epc.supply.data.vo.resp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 123
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SupplyVendorVO {

    /**
     * 主键 id
     */
    private Long id;

    /**
     * 供应商名称
     */
    private String name;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 地址
     */
    private String address;



}
