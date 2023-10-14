package com.cbim.epc.supply.common.base.exception;

import lombok.AllArgsConstructor;

/**
 * @Author: liushaobin
 * @Date: 2023/04/07 13:38
 * @Description:
 */
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN_CODE(500, "未知异常!"),
    SERVER_BUSY(400,"服务器繁忙,请稍后重试!"),
    ;
    public int code;
    public String msg;

    public static String valueMsg(int code) {
        ErrorCode[] values = ErrorCode.values();
        for(ErrorCode var : values) {
            if (var.code == code) {
                return var.msg;
            }
        }
        return UNKNOWN_CODE.msg;
    }
}
