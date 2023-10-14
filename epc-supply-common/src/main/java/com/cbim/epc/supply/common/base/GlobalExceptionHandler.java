package com.cbim.epc.supply.common.base;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.cbim.epc.base.constant.HttpCodeEnum;
import com.cbim.epc.base.exception.BaseException;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.common.base.exception.EpcBusException;
import com.cbim.epc.supply.common.base.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public SingleResponse handle(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException) exception;
            List<ObjectError> errs = exs.getBindingResult().getAllErrors();
            String e = "";
            for (ObjectError err : errs) {
                e += err.getDefaultMessage();
            }
            log.error(exs.getLocalizedMessage());
            return SingleResponse.buildFailure(400, e);
        }
        if (exception instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            List<String> exceptionMessageList = exs.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            String e = StrUtil.join(System.lineSeparator(), exceptionMessageList);
            log.error(exs.getLocalizedMessage());
            return SingleResponse.buildFailure(400, e);
        }
        if (exception instanceof BaseException) {
            log.error(ExceptionUtil.stacktraceToString(exception));
            return SingleResponse.buildFailure(((BaseException) exception).getErrCode(), ((BaseException) exception).getMessage());
        }
        log.error(ExceptionUtil.stacktraceToString(exception));
        return SingleResponse.buildFailure(HttpCodeEnum.SYSTEM_ERROR.getCode(),HttpCodeEnum.SYSTEM_ERROR.getMessage());
    }

    @ExceptionHandler(EpcBusException.class)
    public SingleResponse busException(EpcBusException e){
        int errCode = e.getErrCode();
        log.error(ExceptionUtil.stacktraceToString(e.getCause()));
        return SingleResponse.buildFailure(errCode, ErrorCode.valueMsg(errCode));
    }

}
