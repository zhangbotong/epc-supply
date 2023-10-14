package com.cbim.epc.supply.common.config.Interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cbim.epc.base.interceptor.UserInterceptor;
import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.base.usercontext.UserDTO;
import com.cbim.uc.util.ThreadLocalUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@ConditionalOnExpression(value = "('${cbim.uc.sdk.type}'.equals('WS') || '${cbim.uc.sdk.type}'.equals('APP')) && '${cbim.uc.sdk.filter}'.equals('true')")
public class MvcInterceptorManager implements WebMvcConfigurer, HandlerInterceptor {
    @Resource
    private UserInterceptor userInterceptor;
    @Resource
    private OperationLogInterceptor operationLogInterceptor;
    @Resource
    private MainInterceptor mainInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则，/**表示拦截所有请求
        // excludePathPatterns 用户排除拦截
        addUcConfigurationInterceptors(registry);//用户登录
        registry.addInterceptor(mainInterceptor).addPathPatterns("/**");

        Environment bean = SpringUtil.getBean(Environment.class);
        String active = bean.getProperty("spring.profiles.active");
        if (!active.equalsIgnoreCase("dev")) {
            registry.addInterceptor(userInterceptor).addPathPatterns("/**")
                    .excludePathPatterns("/openapi/**")
                    .excludePathPatterns("/uc/logout")
                    .excludePathPatterns("/base/log/**")
                    .excludePathPatterns("/**.html");
        }
        registry.addInterceptor(operationLogInterceptor).addPathPatterns("/**");
    }


    public void addUcConfigurationInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(this)
                .addPathPatterns("/**").excludePathPatterns("/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userJsonStr= "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"用户-集团\",\n" +
                "        \"entCode\": \"00\",\n" +
                "        \"tagRelDepIds\": [\n" +
                "            1536551100922499073\n" +
                "        ],\n" +
                "        \"tagCodes\": [\n" +
                "            \"A-1-1\",\n" +
                "            \"A-1-2\",\n" +
                "            \"A-J-1\",\n" +
                "            \"epcadml3\",\n" +
                "            \"epcentl2\",\n" +
                "            \"epcoutl2\",\n" +
                "            \"epcusrl1\",\n" +
                "            \"01\"\n" +
                "        ]\n" +
                "    }";
        UserDTO userDTO = JSON.parseObject(userJsonStr, new TypeReference<UserDTO>() {});
        UserContext.setUser(userDTO);

        ThreadLocalUtil.remove("jwt");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
