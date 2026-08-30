-- Login 模块 MySQL 8.0+ 初始化脚本
-- 来源：login-orm-flex persistence/model 下的 LoginCache、LoginRecord 实体
-- 说明：
--   1. Java 驼峰属性按 MyBatis-Flex 默认规则映射为下划线字段。
--   2. BaseFlexSimpleEntity 继承 insert_time、last_update_time。
--   3. BaseFlexEntity 额外继承 deleted（0：正常，1：已删除）。
--   4. 实体未声明字符串长度；脚本按用途设置合理长度，JSON/权限等不定长数据使用 TEXT。
--   5. UUID 由应用生成，主键预留 VARCHAR(64)，兼容有/无连字符的 UUID。

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ---------------------------------------------------------------------------
-- 登录缓存表
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `login_cache` (
    `login_cache_id`  VARCHAR(64)  NOT NULL COMMENT 'ID，应用生成的UUID',
    `login_token`     VARCHAR(255) NOT NULL COMMENT '登录Token',
    `user_id`         VARCHAR(64)  NOT NULL COMMENT '用户ID',
    `type`            VARCHAR(32)  NOT NULL COMMENT '账号类型（platform、customer等）',
    `user_data`       TEXT         NULL COMMENT '用户登录数据',
    `user_auth`       TEXT         NULL COMMENT '用户权限',
    `expire_time`     DATETIME(6)  NOT NULL COMMENT '过期时间',
    `insert_time`     DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '插入时间',
    `last_update_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                             ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
    PRIMARY KEY (`login_cache_id`),
    UNIQUE KEY `uk_login_cache_login_token` (`login_token`),
    KEY `idx_login_cache_user_type` (`user_id`, `type`),
    KEY `idx_login_cache_expire_time` (`expire_time`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  COMMENT = '登录缓存表';

-- ---------------------------------------------------------------------------
-- 登录记录表
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `login_record` (
    `login_record_id` VARCHAR(64)  NOT NULL COMMENT 'ID，应用生成的UUID',
    `user_id`         VARCHAR(64)  NOT NULL COMMENT '用户ID',
    `phone`           VARCHAR(32)  NULL COMMENT '手机号',
    `account`         VARCHAR(128) NULL COMMENT '账号',
    `type`            VARCHAR(32)  NOT NULL COMMENT '账号类型（platform、customer等）',
    `last_login_time` DATETIME(6)  NULL COMMENT '上次登录时间',
    `login_time`      DATETIME(6)  NOT NULL COMMENT '登录时间',
    `expire_time`     DATETIME(6)  NULL COMMENT '过期时间',
    `login_ip`        VARCHAR(45)  NULL COMMENT '登录IP，兼容IPv4和IPv6',
    `device_info`     VARCHAR(512) NULL COMMENT '设备信息',
    `ip_location`     VARCHAR(255) NULL COMMENT 'IP所属地',
    `fail_count`      INT          NOT NULL DEFAULT 0 COMMENT '失败次数',
    `client_type`     VARCHAR(32)  NULL COMMENT '登录客户端类型（APP、WEB）',
    `login_token`     VARCHAR(255) NOT NULL COMMENT '登录Token',
    `insert_time`     DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '插入时间',
    `last_update_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                             ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
    `deleted`         TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除标志（0：正常，1：已删除）',
    PRIMARY KEY (`login_record_id`),
    UNIQUE KEY `uk_login_record_login_token` (`login_token`),
    KEY `idx_login_record_user_type_time` (`user_id`, `type`, `login_time`),
    KEY `idx_login_record_account_type` (`account`, `type`),
    KEY `idx_login_record_phone_type` (`phone`, `type`),
    KEY `idx_login_record_expire_time` (`expire_time`),
    KEY `idx_login_record_deleted_time` (`deleted`, `login_time`),
    CONSTRAINT `chk_login_record_deleted` CHECK (`deleted` IN (0, 1)),
    CONSTRAINT `chk_login_record_fail_count` CHECK (`fail_count` >= 0)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  COMMENT = '登录记录表';

SET FOREIGN_KEY_CHECKS = 1;
