package com.cbim.epc.supply.data.vo.resp;

import lombok.Data;

@Data
public class ContractOrderVO {

    private String orderNo;

    private String orderName;

    /**
     * 订单工程量
     */
    private String orderEngQuantity;

    private String orderAmount;

}
