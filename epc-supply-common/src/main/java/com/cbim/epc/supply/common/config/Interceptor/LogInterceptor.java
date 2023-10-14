package com.cbim.epc.supply.common.config.Interceptor;


import com.cbim.epc.supply.common.domain.CommonLog;

public interface LogInterceptor {

    void handleLog(CommonLog log);
}
