package com.example.frame.exception;

import com.example.frame.model.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 自定义异常处理
 */

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public JsonData handle(Exception e) {
        // If the exception is sub-exception of RuntimeException.
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.info("[业务异常]{}", e.getMessage());
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        } else {
            log.info("[系统异常]{}", e.getMessage());
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}