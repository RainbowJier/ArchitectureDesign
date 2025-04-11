package com.example.frame.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/5 16:28
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    /**
     * 账号
     */
    private Long accountNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户级别
     */
    private String auth;
}
