package com.example.system.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎账号注册实体类
 * @Date: 2024/10/5 14:28
 * @Version: 1.0
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 验证码
     */
    private String code;
}
