package com.example.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Description：Util to serialize the data to json.
 * @Author： RainbowJier
 * @Data： 2024/8/12 22:04
 */

@Slf4j
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // Set the availability of single quotes.
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // Serialize all properties of the object.
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        // If there are other properties added during the deserialization, no exception will be thrown.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // If the object is null, no exception will be thrown.
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // Cancel the conversion format of the time, default is timestamp.
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // And the time format needs to be set.
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Convert object to String.
     */
    public static String objToString(Object obj) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Json Format Error: {}", e);
        }
        return jsonStr;
    }
    /**
     * Convert Object to Json String.
     */
    public static String objToJsonStr(Object obj) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Json Format Error: {}", e);
        }
        return jsonStr;
    }


    /**
     * Convert jsonStr to Object.
     */
    public static <T> T jsonStrToObj(String jsonStr, Class<T> beanType) {
        T obj = null;
        try {
            obj = mapper.readValue(jsonStr, beanType);
        } catch (Exception e) {
            log.error("Json Format Error: {}", e);
        }
        return obj;
    }

    /**
     * Convert json to Object list.
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = mapper.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            log.error("Json Format Error: {}", e);
        }
        return null;
    }

    /**
     * Convert Object to Array.
     */
    public static byte[] objToBytes(Object obj) {
        byte[] byteArr = null;
        try {
            byteArr = mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("Json Format Error: {}", e);
        }
        return byteArr;
    }

    /**
     * Convert Byte Array to Object.
     */
    public static <T> T bytesToObj(byte[] byteArr, Class<T> beanType) {
        T obj = null;
        try {
            obj = mapper.readValue(byteArr, beanType);
        } catch (Exception e) {
            log.error("Json Format Error: {}", e);
        }
        return obj;
    }
}