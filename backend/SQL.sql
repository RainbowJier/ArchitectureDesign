-- 创建数据库 general_db
CREATE DATABASE general_db;

-- 使用 general_db 数据库
USE general_db;

-- 用户表
CREATE TABLE sys_user
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名',
    email       VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    password    VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    account     VARCHAR(50)  NOT NULL COMMENT '账号',
    salt        VARCHAR(50) COMMENT '用于加盐加密',
    phone       VARCHAR(20) UNIQUE COMMENT '手机号',
    status      TINYINT  DEFAULT 1 COMMENT '状态：0=禁用，1=启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);


