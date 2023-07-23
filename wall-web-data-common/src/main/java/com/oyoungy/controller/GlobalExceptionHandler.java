package com.oyoungy.controller;

import com.oyoungy.exceptions.WallBaseException;
import com.oyoungy.response.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultDTO<Void> exceptionHandler(Exception e) {

        // 不是所有异常都需要打印完整堆栈，后续可以定义内部的Exception，便于判断
        if (e instanceof IllegalArgumentException || e instanceof WallBaseException) {
            log.warn("[ControllerException] http request failed, message is {}.", e.getMessage());
        } else if (e instanceof HttpMessageNotReadableException) {
            log.warn("[ControllerException] invalid http request params, exception is {}.", e.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.warn("[ControllerException] invalid http request method, exception is {}.", e.getMessage());
        } else {
            log.error("[ControllerException] http request failed.", e);
        }
        return ResultDTO.failed(ExceptionUtils.getMessage(e));
    }
}
