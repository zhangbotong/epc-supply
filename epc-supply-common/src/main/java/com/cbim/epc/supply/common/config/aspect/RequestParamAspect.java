package com.cbim.epc.supply.common.config.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.cbim.epc.base.exception.BaseException;
import com.cbim.epc.base.usercontext.UserDTO;
import com.cbim.epc.supply.common.config.Interceptor.LogInterceptor;
import com.cbim.epc.supply.common.config.Interceptor.LogInterceptorManager;
import com.cbim.epc.supply.common.config.annoation.NoLog;
import com.cbim.epc.supply.common.domain.CommonLog;
import com.cbim.epc.supply.common.utils.LoginUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Aspect
@Component
@Order(1)
@Slf4j
public class RequestParamAspect {

    private List<LogInterceptor> interceptors;

    private final Executor executor = SpringUtil.getBean("epcThreadPool");

    @Value("${epc.system.log-enabled:true}")
    private Boolean logEnable;
    @Value("${epc.system.login-check:false}")
    private Boolean loginCheck;

    public RequestParamAspect() {
        try {
            this.interceptors = SpringUtil.getBean(LogInterceptorManager.class).getInterceptors();
        } catch (Exception e) {
            this.interceptors = new ArrayList<>();
        }
    }

    @Around("@within(org.springframework.web.bind.annotation.RestController)||" +
            "@within(org.springframework.stereotype.Controller)")
    public Object after(ProceedingJoinPoint joinPoint) throws Throwable {
        if (logUnable(joinPoint)) {
            return joinPoint.proceed();
        }

        String userId = getUserId();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        long start = System.currentTimeMillis();
        Object result = new Object();
        StringBuffer resultDetails = new StringBuffer();
        int resCode = 1;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            result = e.toString();
            resCode = 0;
            buildDetails(resultDetails, e);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            CommonLog commonLog = CommonLog.builder()
                    .userId(userId)
                    .requestTime(new Date())
                    .requestUri(request.getRequestURI())
                    .requestType(request.getMethod())
                    .requestMethod(joinPoint.getSignature().toString())
                    .requestParam(JSONObject.toJSONString(filterArgs(joinPoint.getArgs())))
                    .requestResult(JSONObject.toJSONString(result))
                    .resultCode(resCode)
                    .costTime(end - start)
                    .requestId(IdUtil.getSnowflake().nextIdStr())
                    .resultDetails(resultDetails.toString())
                    .build();
            handleInterceptor(commonLog);
        }
        return result;
    }

    private static void buildDetails(StringBuffer resultDetails, Exception e) {
        boolean instanceOf = e instanceof BaseException;
        if (!instanceOf) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            Arrays.stream(stackTrace).collect(Collectors.toList())
                    .forEach(stackTraceElement -> resultDetails
                            .append(stackTraceElement.toString())
                            .append("\n"));
        }
    }

    private String getUserId() {
        String userId = "";
        if (loginCheck) {
            UserDTO loginUser = LoginUserUtil.getLoginUser();
            userId = String.valueOf(loginUser.getId());
        }
        return userId;
    }

    private boolean logUnable(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!logEnable) {
            return true;
        }
        return isNoLog(joinPoint);
    }

    private void handleInterceptor(CommonLog commonLog) {
        if (CollectionUtil.isNotEmpty(interceptors)) {
            interceptors.forEach(item -> executor.execute(() -> item.handleLog(commonLog)));
        }
    }

    private boolean isNoLog(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Class<?> aClass = joinPoint.getTarget().getClass();
        MethodSignature msg = (MethodSignature) joinPoint.getSignature();
        return aClass.getAnnotation(NoLog.class) != null ||
                aClass.getMethod(msg.getName(), msg.getParameterTypes()).getAnnotation(NoLog.class) != null;
    }

    private List<Object> filterArgs(Object[] objects) {
        return Arrays.stream(objects).filter(obj -> !(obj instanceof MultipartFile)
                && !(obj instanceof HttpServletResponse)
                && !(obj instanceof HttpServletRequest)).collect(Collectors.toList());
    }
}


