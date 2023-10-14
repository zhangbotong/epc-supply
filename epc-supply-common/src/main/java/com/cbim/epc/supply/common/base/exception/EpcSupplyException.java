package com.cbim.epc.supply.common.base.exception;

import com.cbim.epc.base.exception.EpcException;

/**
 * epc 项目 supply服务异常
 * @author 123
 *
 */
public class EpcSupplyException extends EpcException {
    public EpcSupplyException(String message) {
        super(message);
    }

    public EpcSupplyException(Integer code, String message) {
        super(code, message);
    }



}
