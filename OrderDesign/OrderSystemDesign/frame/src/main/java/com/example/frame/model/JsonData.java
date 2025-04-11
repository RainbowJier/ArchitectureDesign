package com.example.frame.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.frame.enums.BizCodeEnum;
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
     * Construct a new JsonData object with the specified code, data and message.
     */
    public <T> T getData(TypeReference<T> typeReference) {
        return JSON.parseObject(JSON.toJSONString(data), typeReference);
    }

    /**
     * Successful without parameters.
     */
    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    /**
     * Successful with response data.
     */
    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

    public static JsonData buildSuccess(String msg) {
        return new JsonData(0, null, msg);
    }

    public static JsonData buildSuccess(Object data,String msg) {
        return new JsonData(0, data, msg);
    }

    /**
     * Error with description.
     */
    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    /**
     * Custom response code and message.
     */
    public static JsonData buildCodeAndMsg(int code, String msg) {
        return new JsonData(code, null, msg);
    }

    /**
     * Input enums and return JsonData.
     */
    public static JsonData buildResult(BizCodeEnum codeEnum) {
        return JsonData.buildCodeAndMsg(codeEnum.getCode(), codeEnum.getMessage());
    }
}