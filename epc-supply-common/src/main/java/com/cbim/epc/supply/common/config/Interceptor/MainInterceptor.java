package com.cbim.epc.supply.common.config.Interceptor;

import cn.hutool.core.util.IdUtil;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: 临时。 入口 log 上下文增加 requestId。可以放到user拦截器中或者 base包增加新的拦截器
* @Author: liushaobin
*/
@Component
@Slf4j
public class MainInterceptor implements HandlerInterceptor {

    public static final String REQUEST_ID = "Request-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 优先使用Headers中获取的Request-Id，如果没有则创建一个.
        String requestId = request.getHeader(REQUEST_ID);
        if (Strings.isNullOrEmpty(requestId)) {
            requestId = IdUtil.fastSimpleUUID();
        }
        // log 上下文增加requestId
        MDC.put(REQUEST_ID,requestId);
        return true;
    }
}
