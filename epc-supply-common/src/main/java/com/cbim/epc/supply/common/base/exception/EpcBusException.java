package com.cbim.epc.supply.common.base.exception;

import com.cbim.epc.base.exception.BaseException;

/**
 * @Author: liushaobin
 * @Date: 2023/04/07 11:29
 * @Description: 自定义异常实体
 */
public class EpcBusException extends BaseException {

    public EpcBusException(ErrorCode errorCode, Throwable e) {
        super(errorCode.code, e.getMessage(),e);
    }

    public void updateBusMsg() {
        super.setErrorMessage(ErrorCode.valueMsg(this.getErrCode()));
    }
}
