package com.example.common.util;

import com.example.common.enums.BizCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description：Jsondata is a class that represents a JSON data object.
 * @Author： RainbowJier
 * @Data： 2024/8/12 21:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JsonData {
    private Integer code;

    private Object data;

    private String msg;

    /**
     * Successful response object.
     */
    public static JsonData buildSuccess() {
        return new JsonData(BizCode.SUCCESS.getCode(), null, BizCode.SUCCESS.getMessage());
    }

    public static JsonData buildSuccess(Object data) {
        return new JsonData(BizCode.SUCCESS.getCode(), data, BizCode.SUCCESS.getMessage());
    }

    public static JsonData buildSuccess(Object data,String msg) {
        return new JsonData(BizCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * Error with description.
     */
    public static JsonData buildError() {
        return new JsonData(BizCode.FAILED.getCode(), null, BizCode.FAILED.getMessage());
    }

    public static JsonData buildError(String msg) {
        return new JsonData(BizCode.FAILED.getCode(), null, msg);
    }

    public static JsonData buildError(BizCode bizCode) {
        return new JsonData(bizCode.getCode(), null, bizCode.getMessage());
    }

    /**
     * Custom response object
     */
    public static JsonData buildCodeAndMsg(int code,String msg) {
        return new JsonData(code, null, msg);
    }
}