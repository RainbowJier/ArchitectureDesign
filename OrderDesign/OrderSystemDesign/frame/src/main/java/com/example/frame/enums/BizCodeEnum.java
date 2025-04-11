package com.example.frame.enums;

import lombok.*;

/**
 * @Description：TODO
 * @Author： RainbowJier
 * @Data： 2024/8/12 21:17
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BizCodeEnum {
    /**
     * 用户登录
     */
    TOKEN_INVALID(10000, "token 校验失败"),
    ACCOUNT_UNLOGIN(10001, "用户未登录"),

    /**
     * Order
     */
    ORDER_CONFIRM_PRICE_FAIL(20001, "创建订单-验价失败"),
    ORDER_CONFIRM_REPEAT(20002, "订单恶意-重复提交"),
    ORDER_CONFIRM_TOKEN_EQUAL_FAIL(280003, "订单令牌缺少"),
    ORDER_CONFIRM_TOKEN_VERIFY_FAIL(280004, "订单令牌校验失败"),
    ORDER_CONFIRM_NOT_EXIST(280005, "订单不存在"),

    /**
     * Payment.
     */
    PAY_ORDER_FAIL(300001, "创建⽀付订单失败"),
    PAY_ORDER_CALLBACK_SIGN_FAIL(300002, "⽀付订单回调验证签失败"),
    PAY_ORDER_CALLBACK_NOT_SUCCESS(300003, "⽀付宝回调更新订单失败"),
    PAY_ORDER_NOT_EXIST(300005, "订单不存在"),
    PAY_ORDER_STATE_ERROR(300006, "订单状态不正常"),
    PAY_ORDER_PAY_TIMEOUT(300007, "订单⽀付超时");


    private String message;

    private int code;

    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
