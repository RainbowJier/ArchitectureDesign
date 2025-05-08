package com.example.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户名
     */
    private String username;
    /**
     * 加密后的密码
     */
    private String password;
    /**
     * 用于加盐加密
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 状态：0=禁用，1=启用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}

