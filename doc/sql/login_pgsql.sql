-- Login 模块 PostgreSQL 14+ 初始化脚本
-- 来源：login-orm-flex persistence/model 下的 LoginCache、LoginRecord 实体
-- 说明：
--   1. Java 驼峰属性按 MyBatis-Flex 默认规则映射为下划线字段。
--   2. BaseFlexSimpleEntity 继承 insert_time、last_update_time。
--   3. BaseFlexEntity 额外继承 deleted（0：正常，1：已删除）。
--   4. 实体未声明字符串长度；脚本按用途设置合理长度，JSON/权限等不定长数据使用 TEXT。
--   5. UUID 由应用生成，主键预留 VARCHAR(64)，兼容有/无连字符的 UUID。
--   6. LocalDateTime 映射为 TIMESTAMP WITHOUT TIME ZONE，时区转换由应用统一处理。

BEGIN;

-- 自动维护 last_update_time。应用层虽会自动填充，该触发器可保障直接执行 SQL 时字段仍准确。
CREATE OR REPLACE FUNCTION set_last_update_time()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.last_update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;

COMMENT ON FUNCTION set_last_update_time() IS '更新数据时自动刷新最后更新时间';

-- ---------------------------------------------------------------------------
-- 登录缓存表
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS login_cache (
    login_cache_id   VARCHAR(64)  NOT NULL,
    login_token      VARCHAR(255) NOT NULL,
    user_id          VARCHAR(64)  NOT NULL,
    type             VARCHAR(32)  NOT NULL,
    user_data        TEXT,
    user_auth        TEXT,
    expire_time      TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    insert_time      TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_time TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_login_cache PRIMARY KEY (login_cache_id)
);

COMMENT ON TABLE login_cache IS '登录缓存表';
COMMENT ON COLUMN login_cache.login_cache_id IS 'ID，应用生成的UUID';
COMMENT ON COLUMN login_cache.login_token IS '登录Token';
COMMENT ON COLUMN login_cache.user_id IS '用户ID';
COMMENT ON COLUMN login_cache.type IS '账号类型（platform、customer等）';
COMMENT ON COLUMN login_cache.user_data IS '用户登录数据';
COMMENT ON COLUMN login_cache.user_auth IS '用户权限';
COMMENT ON COLUMN login_cache.expire_time IS '过期时间';
COMMENT ON COLUMN login_cache.insert_time IS '插入时间';
COMMENT ON COLUMN login_cache.last_update_time IS '最后更新时间';

CREATE UNIQUE INDEX IF NOT EXISTS uk_login_cache_login_token
    ON login_cache (login_token);
CREATE INDEX IF NOT EXISTS idx_login_cache_user_type
    ON login_cache (user_id, type);
CREATE INDEX IF NOT EXISTS idx_login_cache_expire_time
    ON login_cache (expire_time);

DROP TRIGGER IF EXISTS trg_login_cache_last_update_time ON login_cache;
CREATE TRIGGER trg_login_cache_last_update_time
    BEFORE UPDATE ON login_cache
    FOR EACH ROW
    EXECUTE FUNCTION set_last_update_time();

-- ---------------------------------------------------------------------------
-- 登录记录表
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS login_record (
    login_record_id  VARCHAR(64)  NOT NULL,
    user_id          VARCHAR(64)  NOT NULL,
    phone            VARCHAR(32),
    account          VARCHAR(128),
    type             VARCHAR(32)  NOT NULL,
    last_login_time  TIMESTAMP(6) WITHOUT TIME ZONE,
    login_time       TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    expire_time      TIMESTAMP(6) WITHOUT TIME ZONE,
    login_ip         VARCHAR(45),
    device_info      VARCHAR(512),
    ip_location      VARCHAR(255),
    fail_count       INTEGER      NOT NULL DEFAULT 0,
    client_type      VARCHAR(32),
    login_token      VARCHAR(255) NOT NULL,
    insert_time      TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_time TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted          SMALLINT     NOT NULL DEFAULT 0,
    CONSTRAINT pk_login_record PRIMARY KEY (login_record_id),
    CONSTRAINT chk_login_record_deleted CHECK (deleted IN (0, 1)),
    CONSTRAINT chk_login_record_fail_count CHECK (fail_count >= 0)
);

COMMENT ON TABLE login_record IS '登录记录表';
COMMENT ON COLUMN login_record.login_record_id IS 'ID，应用生成的UUID';
COMMENT ON COLUMN login_record.user_id IS '用户ID';
COMMENT ON COLUMN login_record.phone IS '手机号';
COMMENT ON COLUMN login_record.account IS '账号';
COMMENT ON COLUMN login_record.type IS '账号类型（platform、customer等）';
COMMENT ON COLUMN login_record.last_login_time IS '上次登录时间';
COMMENT ON COLUMN login_record.login_time IS '登录时间';
COMMENT ON COLUMN login_record.expire_time IS '过期时间';
COMMENT ON COLUMN login_record.login_ip IS '登录IP，兼容IPv4和IPv6';
COMMENT ON COLUMN login_record.device_info IS '设备信息';
COMMENT ON COLUMN login_record.ip_location IS 'IP所属地';
COMMENT ON COLUMN login_record.fail_count IS '失败次数';
COMMENT ON COLUMN login_record.client_type IS '登录客户端类型（APP、WEB）';
COMMENT ON COLUMN login_record.login_token IS '登录Token';
COMMENT ON COLUMN login_record.insert_time IS '插入时间';
COMMENT ON COLUMN login_record.last_update_time IS '最后更新时间';
COMMENT ON COLUMN login_record.deleted IS '逻辑删除标志（0：正常，1：已删除）';

CREATE UNIQUE INDEX IF NOT EXISTS uk_login_record_login_token
    ON login_record (login_token);
CREATE INDEX IF NOT EXISTS idx_login_record_user_type_time
    ON login_record (user_id, type, login_time);
CREATE INDEX IF NOT EXISTS idx_login_record_account_type
    ON login_record (account, type);
CREATE INDEX IF NOT EXISTS idx_login_record_phone_type
    ON login_record (phone, type);
CREATE INDEX IF NOT EXISTS idx_login_record_expire_time
    ON login_record (expire_time);
CREATE INDEX IF NOT EXISTS idx_login_record_deleted_time
    ON login_record (deleted, login_time);

DROP TRIGGER IF EXISTS trg_login_record_last_update_time ON login_record;
CREATE TRIGGER trg_login_record_last_update_time
    BEFORE UPDATE ON login_record
    FOR EACH ROW
    EXECUTE FUNCTION set_last_update_time();

COMMIT;
