package com.cbim.epc.supply.common.config;

import com.cbim.epc.supply.common.config.Interceptor.LogDbInterceptor;
import com.cbim.epc.supply.common.config.Interceptor.LogInterceptorManager;
import com.cbim.epc.supply.common.config.Interceptor.LogPrintInterceptor;
import com.cbim.epc.supply.common.config.Interceptor.LogQyWeChatInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class CommonLogConfig {

    @Resource
    private LogDbInterceptor dbLogInterceptor;

    @Resource
    private LogPrintInterceptor printLogInterceptor;

    @Resource
    private LogQyWeChatInterceptor qyWeChatInterceptor;

    @Bean
    public LogInterceptorManager logInterceptor(){
        LogInterceptorManager logInterceptor = new LogInterceptorManager();
        logInterceptor.addInterceptor(dbLogInterceptor);
        logInterceptor.addInterceptor(printLogInterceptor);
        logInterceptor.addInterceptor(qyWeChatInterceptor);
        return logInterceptor;
    }
}
