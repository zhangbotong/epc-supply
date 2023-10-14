package com.cbim.epc.supply.common.config.Interceptor;

import com.cbim.epc.supply.common.domain.CommonLog;
import com.cbim.epc.supply.common.mapper.CommonLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogDbInterceptor implements LogInterceptor {

    private final CommonLogMapper logMapper;

    @Override
    public void handleLog(CommonLog commonLog) {
        logMapper.insert(commonLog);
        log.info("[{}]:add commonLog",Thread.currentThread().getName());
    }
}
