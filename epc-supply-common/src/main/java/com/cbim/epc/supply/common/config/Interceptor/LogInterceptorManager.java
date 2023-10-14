package com.cbim.epc.supply.common.config.Interceptor;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LogInterceptorManager {
    private final List<LogInterceptor> interceptors = new ArrayList<>();

    public void addInterceptor(LogInterceptor interceptor){
        interceptors.add(interceptor);
    }
}
