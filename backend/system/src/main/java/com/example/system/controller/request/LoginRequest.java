package com.example.system.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎用户账号登录
 * @Date: 2024/10/5 16:12
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    // private String phone;

    private String email;

    private String username;

    private String password;
}
