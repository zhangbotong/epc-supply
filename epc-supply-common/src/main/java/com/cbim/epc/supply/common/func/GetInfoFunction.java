package com.cbim.epc.supply.common.func;


import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.sdk.log.support.service.IParseFunction;
import org.springframework.stereotype.Component;

/**
* @Description: 实现获取用户上下文信息
 * 功能 前面拼接 _GET_INFO
 * 使用方式：{_GET_INFO{userId}} 或者 {_GET_INFO{userName}}
* @Author: liushaobin
*/
@Component
public class GetInfoFunction implements IParseFunction {
    final String FUNCTION_NAME="_GET_INFO";
    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public String apply(Object value) {
        String str = String.valueOf(value);
        switch (str){
            case "userId":
                return String.valueOf(UserContext.getUser().getId());
            case "userName":
                return String.valueOf(UserContext.getUser().getName());
        }
        return str;
    }
}
