package com.cbim.epc.supply.common.config.Interceptor;

import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.base.usercontext.UserDTO;
import com.cbim.epc.sdk.log.beans.OperationLog;
import com.cbim.epc.sdk.log.context.OperationLogContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: operationLog增加 requestId、userId
* @Author: liushaobin
*/
@Component
@Slf4j
public class OperationLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UserDTO user = UserContext.getUser();
        String userStr = "";
        if ( null != user && null != user.getId()) {
            userStr = String.valueOf(user.getId());
        }
        // log sdk增加 requestId、userId
        OperationLogContext.setOperationLog(OperationLog.builder()
                .requestId(MDC.get(MainInterceptor.REQUEST_ID))
                .operatorId(userStr)
                .build());
        return true;
    }
}
