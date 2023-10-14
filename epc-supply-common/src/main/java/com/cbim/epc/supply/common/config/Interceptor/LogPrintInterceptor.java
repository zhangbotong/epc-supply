package com.cbim.epc.supply.common.config.Interceptor;

import com.cbim.epc.supply.common.domain.CommonLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogPrintInterceptor implements LogInterceptor {
    @Override
    public void handleLog(CommonLog commonLog) {
        if (log.isInfoEnabled()) {
            log.info("=======CommonLog:[{}]", commonLog.toString());
        }
    }
}
