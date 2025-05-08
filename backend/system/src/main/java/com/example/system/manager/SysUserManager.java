package com.example.system.manager;

import com.example.common.util.JsonData;
import com.example.system.model.entity.SysUser;

import java.util.List;


public interface SysUserManager {
    int insert(SysUser sysUser);

    SysUser selectOne(String email);

    List<SysUser> selectAllUsers();

    int update(SysUser sysUser);
}
