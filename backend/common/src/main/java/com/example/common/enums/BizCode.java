package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description：TODO
 * @Author： RainbowJier
 * @Data： 2024/8/12 21:17
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BizCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),


    /**
     * 账号登录
     */
    ACCOUNT_REPEAT(250001, "账号已经存在"),
    ACCOUNT_UNREGISTER(250002, "账号不存在"),
    ACCOUNT_PWD_ERROR(250003, "账号或者密码错误"),
    ACCOUNT_UNLOGIN(250004, "账号未登录"),
    PHONE_REPEAT(250005, "当前手机号已经注册"),
    EMAIL_REPEAT(250006, "当前邮箱已经注册"),
    FAIL_SEND_EMAIL(250007, "邮箱发送失败"),
    RESET_PASSWORD_LIMIT(250008,"重置密码次数上限"),
    RESET_PASSWORD_FAIL(250009, "重置密码失败"),



    /**
     * 分组
     */
    GROUP_REPEAT(23001, "分组名重复"),
    GROUP_OPER_FAIL(23503, "分组名操作失败"),
    GROUP_NOT_EXIST(23404, "分组不存在"),
    GROUP_ADD_FAILED(23405, "分组添加失败"),

    /**
     * 验证码
     */
    CODE_TO_ERROR(240001, "接收号码不合规"),
    CODE_LIMITED(240002, "验证码发送过快"),
    CODE_ERROR(240003, "验证码错误"),
    CODE_CAPTCHA_ERROR(240004, "图形验证码错误"),
    CODE_CAPTCHA_EXCEPTION(240005, "图形验证码发送异常"),



    /**
     * Stream operation.
     */
    CONTROL_FLOW(500101, "限流控制"),
    CONTROL_DEGRADE(500201, "降级控制"),
    CONTROL_AUTH(500301, "认证控制"),



    /**
     * Files
     */
    FILE_UPLOAD_USER_IMG_FAIL(700101, "⽤户头像⽂件上传失败"),

    /**
     * 数据库路由信息
     */
    DB_ROUTE_NOT_FOUND(83002, "数据库路由未找到"),


    /**
     * MQ
     */
    MQ_CONSUME_EXCEPTION( 900101, "MQ消费异常");

    private String message;

    private int code;

    BizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
