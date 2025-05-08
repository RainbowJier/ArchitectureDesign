package com.example.system.manager.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.common.util.JsonData;
import com.example.system.manager.SysUserManager;
import com.example.system.mapper.SysUserMapper;
import com.example.system.model.entity.SysUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class SysUserManagerImpl implements SysUserManager {

    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    public int insert(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public SysUser selectOne(String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getEmail, email)
                .eq(SysUser::getStatus, 1);

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysUser> selectAllUsers() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        return sysUsers;
    }


    @Override
    public int update(SysUser sysUser) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(SysUser::getId, sysUser.getId())
                .eq(SysUser::getStatus, 1);

        return sysUserMapper.update(sysUser,null);
    }
}
