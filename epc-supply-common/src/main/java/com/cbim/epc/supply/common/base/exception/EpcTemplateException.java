package com.cbim.epc.supply.common.base.exception;

import com.cbim.epc.base.exception.EpcException;

/**
 * epc 项目 Template服务异常
 * @author 123
 *
 */
public class EpcTemplateException extends EpcException {
    public EpcTemplateException(String message) {
        super(message);
    }

    public EpcTemplateException(Integer code, String message) {
        super(code, message);
    }



}
