package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2025-04-17 16:39:01
 */

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


}

