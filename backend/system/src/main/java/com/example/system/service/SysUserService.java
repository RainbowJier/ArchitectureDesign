package com.example.system.service;

import com.example.common.util.JsonData;
import com.example.system.controller.request.LoginRequest;
import com.example.system.controller.request.RegisterRequest;
import com.example.system.controller.request.ResetPwdRequest;


/**
 * (SysUser)表服务接口
 *
 * @author makejava
 * @since 2025-04-17 16:39:02
 */
public interface SysUserService {
    JsonData register(RegisterRequest registerRequest);

    JsonData login(LoginRequest loginRequest);

    JsonData reset(ResetPwdRequest resetPwdRequest);

    JsonData selectAllUsers();
}

