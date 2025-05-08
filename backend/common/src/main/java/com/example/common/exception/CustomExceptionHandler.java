package com.example.common.exception;

import com.example.common.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description：TODO
 * @Author： RainbowJier
 * @Data： 2024/8/12 21:37
 */

// Response Json Data.

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
            return JsonData.buildError();
        }
    }
}