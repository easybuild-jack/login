# Login 登录日志模块

> 基于 `easyfk` 基础框架的**员工登录 / 登出行为日志**模块，作为 Auth 权限模块的补充，集中持久化员工的登录与登出记录，便于安全审计与异常登录分析，同时支持 **Spring Cloud（Feign）** 与 **Apache Dubbo** 两种远程调用方式，方便单体或微服务架构集成。

- **项目版本**：`<latest>`（请以实际发布的最新版本为准）
- **Maven Group**：`com.mcst`
- **构建工具**：Gradle（版本以根 `gradle/wrapper/gradle-wrapper.properties` 为准）
- **JDK**：Java 21+
- **框架**：Spring Boot 3.x、Spring Cloud OpenFeign、Apache Dubbo 3.x
- **辅助工具**：Lombok、MapStruct Plus、Hutool

---

## 一、项目主要功能

1. **登录 / 登出记录**
   - 持久化员工每次登录与登出行为，包括账号、IP、UA、时间等。
   - 登录成功、登录失败、登出成功等状态分别记录。

2. **在线会话查询**
   - 支持按账号、IP、时间范围等维度查询登录日志。
   - 提供最近一次登录信息、登录次数统计等。

3. **异常登录分析**
   - 内置登录失败次数统计，可用于触发账号锁定策略。
   - 支持按 IP 聚合异常登录记录。

4. **双端接口分离**
   - C 端提供当前用户的最近登录记录。
   - BMS 端提供登录日志的完整查询、导出、统计。

5. **多租户与数据隔离**
   - 内置 `agentId` / `merchantId` 字段，自动按代理商 / 商户维度过滤数据。

6. **国际化与可配置**
   - 多语言错误码（基于 `i18n/loginMessage_*.properties`）。

---

## 二、项目架构设计

### 2.1 模块划分

```
login
├── login-api          接口契约层：DTO / Req / Resp / Param / VO / Service API
├── login-server       服务实现层：Service + ApiServer 实现 + 工具类 + 配置 + 单测
├── login-prd          Web 表现层：Spring Boot Controller
│   ├── login-prd-client     C 端接口
│   └── login-prd-bms        后台管理接口
├── login-orm          持久化层
│   ├── login-orm-api         仓储接口定义（IRepository）
│   ├── login-orm-plus        MyBatis-Plus 实现
│   ├── login-orm-flex        MyBatis-Flex 实现
│   └── login-orm-hibernate   Hibernate/JPA 实现
├── login-remote       远程调用层（可选接入）
│   ├── login-cloud-api         Spring Cloud 客户端：Feign 接口 + Api 适配
│   ├── login-cloud-provider    Spring Cloud 服务端：暴露 /remote/login/** 的 Controller
│   ├── login-dubbo-api         Dubbo 接口 + Api 适配
│   └── login-dubbo-provider    Dubbo 服务端实现
└── doc               数据库脚本与映射规范说明
    ├── sql/login_mysql.sql
    └── sql/login_postgresql.sql
```

### 2.2 层次与调用关系

```
┌──────────────────────────────────────────────────────────────────────┐
│  客户端 / 调用方                                                       │
└──────────────────────────────────────────────────────────────────────┘
            │                           │                        │
            ▼                           ▼                        ▼
     ┌────────────┐               ┌────────────┐           ┌────────────┐
     │  login-prd │               │ login-     │           │ login-     │
     │ -client/-  │               │ cloud-api  │           │ dubbo-api  │
     │  bms       │               │ (Feign 客  │           │ (Dubbo 消  │
     │(Controller │               │ 户端+Api)  │           │ 费端+Api)  │
     └────────────┘               └────────────┘           └────────────┘
            │                           │                        │
            │                           │ HTTP                   │ Dubbo RPC
            ▼                           ▼                        ▼
                          ┌──────────────────────────────────────┐
                          │ login-cloud-provider / login-dubbo-  │
                          │ provider （统一委托给 login-server）  │
                          └──────────────────────────────────────┘
                                              │
                                              ▼
                              ┌──────────────────────────────┐
                              │          login-server         │
                              │   Service + ApiServer 实现   │
                              └──────────────────────────────┘
                                              │
                                              ▼
                              ┌──────────────────────────────┐
                              │  login-orm-*（Plus/Flex/Hib） │
                              │   仓储接口 + 持久化实现        │
                              └──────────────────────────────┘
                                              │
                                              ▼
                                          数据库
```

### 2.3 关键设计点

- **接口与实现分离**：`login-api` 只暴露接口与数据模型；`login-server` 提供本地默认实现，通过 Spring Boot 自动装配以 `@ConditionalOnMissingBean` 形式暴露为 Bean，便于上层覆盖。
- **多 ORM 适配**：`login-orm` 把仓储接口抽到 `login-orm-api`，底层分别在 `login-orm-plus / login-orm-flex / login-orm-hibernate` 中提供实现，业务侧按需引入对应依赖即可切换 ORM。
- **远程调用可插拔**：`login-remote` 同时提供 Feign 和 Dubbo 两套客户端，每套又拆出 `-api`（消费端）与 `-provider`（服务端）。消费端把 `ILoginApi` / `ILogoutApi` 适配到远程 `Remote`，再通过 `@AutoConfiguration` 注册到容器；服务端把 `login-server` 的 `Service` 透出成 HTTP / Dubbo 接口。
- **统一参数模型**：遵循 `doc/实体对象映射关系调整指南.md` 中约定的流向（`Sub → Req → Dto → Entity` / `Entity → Dto → Resp`），结合 MapStruct Plus 的 `@AutoMapper` 自动转换。
- **与 Auth 模块联动**：登录 / 登出事件由 Auth 模块的 `LoginController` / `LogoutController` 主动调用 `ILoginApi` / `ILogoutApi` 写入。
- **高写入吞吐**：登录日志为典型写多读少场景，按 `employeeId` + 时间建立索引，避免查询慢。
- **声明式权限控制**：BMS 端通过 `@AuthResource` / `@ResourceController` 注解自动注册菜单与按钮权限，与 Auth 模块联动。

### 2.4 数据模型

| 表名 | 说明 |
| --- | --- |
| `login_log` | 登录日志表，含员工、IP、UA、登录时间、登录状态、登录方式等 |

完整建表脚本见 [doc/sql/login_mysql.sql](doc/sql/login_mysql.sql)、[doc/sql/login_postgresql.sql](doc/sql/login_postgresql.sql)。

---

## 三、模块详细说明

### 3.1 `login-api`
- 定义业务接口：`ILoginApi`、`ILogoutApi`、`ILoginLogApi` 等，并继承 `IBaseApi` 提供通用 CRUD。
- 维护所有数据传输对象：`dto/`、`request/`、`response/`、`param/`、`vo/`。
- 业务接口方法示例：`recordLogin`、`recordLogout`、`queryByPage`、`queryByEmployeeId`、`queryLastLogin`、`countFailByEmployee` 等。

### 3.2 `login-server`
- `service/`：业务 Service 接口与实现（`LoginServiceImpl`、`LogoutServiceImpl`、`LoginLogServiceImpl`）。
- `impl/`：`ApiServer` 实现，继承 `BaseApiServiceImpl`，把 Service 暴露为对应 API。
- `util/`：IP 解析、UA 解析、登录失败统计等工具类。
- `config/LoginServerConfig`：Spring Boot 自动装配入口。
- `src/test/`：基于 JUnit 5 的单元测试。

### 3.3 `login-prd`
- 基于 `easyfk-web-prd` 的 Spring Boot Web 控制器层。
- 控制器清单：
  - `LoginLogBmsController`：`/api/bms/system/loginLog/**`（分页查询、详情、统计）
  - `LoginLogClientController`：`/api/client/loginLog/**`（当前用户最近登录记录）
- 通过 `@AuthResource` / `@ResourceController` 注解自动注册权限元数据。

### 3.4 `login-orm`
- `login-orm-api`：仓储接口（`ILoginLogRepository`）。
- `login-orm-plus`：基于 MyBatis-Plus 的实现 + 自动装配。
- `login-orm-flex`：基于 MyBatis-Flex 的实现 + 自动装配。
- `login-orm-hibernate`：基于 Hibernate/JPA 的实现 + 自动装配。

> 三个 ORM 实现互斥，按需在业务工程中只引入其中一个，避免冲突。

### 3.5 `login-remote`
- `login-cloud-api`：`FeignClient` 接口 + 继承 `BaseApiRemoteImpl` 的 `Api` 实现；通过 `LoginApiProperties`（`easyfk.config.remote.login.service-id/base-path`）读取服务地址。
- `login-cloud-provider`：`@RestController` 把 `login-server` 的 `Service` 暴露为 `/remote/login/**`。
- `login-dubbo-api`：Dubbo 接口 + `Api` 适配实现。
- `login-dubbo-provider`：Dubbo 服务实现。

### 3.6 `doc`
- `实体对象映射关系调整指南.md`：DTO/Entity/Req/Resp/Param/Sub 之间的 `@AutoMapper` 使用规范。
- `sql/`：MySQL、PostgreSQL 建表脚本。

---

## 四、使用方式

### 4.1 构建与发布

```bash
# 清理并编译全部模块
./gradlew clean build

# 发布到本地 Maven 仓库
./gradlew publishToMavenLocal

# 发布到远程 Nexus（在 build.gradle 中已配置 NexusRepository）
./gradlew publish
```

> Windows 环境可直接使用 `gradlew.bat`。模块统一版本：`<latest>`。

### 4.2 单体应用集成（最简方式）

```groovy
dependencies {
    implementation 'com.mcst:login-api:<latest>'
    implementation 'com.mcst:login-server:<latest>'
    // 选择一种 ORM：
    implementation 'com.mcst:login-orm:login-orm-plus:<latest>'
    // implementation 'com.mcst:login-orm:login-orm-flex:<latest>'
    // implementation 'com.mcst:login-orm:login-orm-hibernate:<latest>'

    // 暴露 HTTP 接口（按端选择）
    implementation 'com.mcst:login-prd:login-prd-bms:<latest>'
    implementation 'com.mcst:login-prd:login-prd-client:<latest>'
}
```

`login-server` 会通过 Spring Boot 自动装配把 `ILoginApi`、`ILogoutApi`、`ILoginLogApi` 等 Bean 注册到容器，Controller 可直接 `@Resource` 注入使用。

### 4.3 微服务集成（Spring Cloud）

**消费端：**

```groovy
dependencies {
    implementation 'com.mcst:login-api:<latest>'
    implementation 'com.mcst:login-remote:login-cloud-api:<latest>'
}
```

```yaml
easyfk:
  config:
    remote:
      login:
        service-id: login-server       # 对应服务在注册中心的服务名
        base-path: /remote              # 可选，默认 /remote
```

**服务端：** 启动 `login-server` 并加入 `login-remote:login-cloud-provider`，即可对外提供 `/remote/login/**` 接口。

### 4.4 微服务集成（Apache Dubbo）

```groovy
// 消费端
dependencies {
    implementation 'com.mcst:login-api:<latest>'
    implementation 'com.mcst:login-remote:login-dubbo-api:<latest>'
}

// 服务端
dependencies {
    implementation 'com.mcst:login-server:<latest>'
    implementation 'com.mcst:login-remote:login-dubbo-provider:<latest>'
}
```

### 4.5 关键配置项

| 配置项 | 含义 | 默认值 |
| --- | --- | --- |
| `easyfk.config.remote.login.service-id` | 远程调用目标服务名 | `login-server` |
| `easyfk.config.remote.login.base-path` | 远程调用基础路径 | `/remote` |

### 4.6 初始化数据库

```bash
# MySQL
mysql -u <user> -p <database> < doc/sql/login_mysql.sql

# PostgreSQL
psql -U <user> -d <database> -f doc/sql/login_postgresql.sql
```

### 4.7 主要 REST 接口一览

| 端 | 路径 | 方法 | 说明 |
| --- | --- | --- | --- |
| BMS | `/api/bms/system/loginLog/queryPage` | GET | 分页查询 |
| BMS | `/api/bms/system/loginLog/detail` | GET | 详情 |
| BMS | `/api/bms/system/loginLog/statistics` | GET | 登录统计 |
| Client | `/api/client/loginLog/last` | GET | 当前用户最近一次登录 |
| Client | `/api/client/loginLog/list` | GET | 当前用户登录记录 |

> 接口均通过 Swagger OpenAPI 注解描述，可在启动后访问 `/swagger-ui.html` 查看完整文档。

> Auth 模块登录 / 登出控制器通过 Feign / Dubbo 调用本模块的 `ILoginApi` / `ILogoutApi`，由 `@Async` 异步写入，不阻塞主链路。

---

## 五、扩展指引

- **新增业务字段**：在 `login-api` 的 `Dto` 中新增字段 → 在 `Entity` 中同步 → 选择对应 ORM 模块更新映射 → 视情况更新 `Resp`、`Req`、`VO`。
- **新增登录事件**：在 `LoginEventEnum` 中新增事件类型 → 在 `login-server` 中补充对应处理逻辑。
- **新增业务接口**：在 `login-api` 暴露方法 → 在 `login-server` 的 `Service` 与 `ApiServer` 中实现 → 选择在 `login-prd` 增加 Controller 或在 `login-remote` 暴露远程能力。
- **切换 ORM**：只保留目标 ORM 模块的依赖（如只想用 MyBatis-Plus，则移除 `login-orm-flex`、`login-orm-hibernate` 依赖）。
- **国际化**：在 `login-server/src/main/resources/i18n/` 下追加语言键值即可。

---

## 六、目录速览

```
login
├── build.gradle                根构建脚本（统一版本、Java 21、Lombok、MapStruct Plus）
├── settings.gradle             多模块声明
├── gradle/, gradlew, gradlew.bat
├── login-api/                  接口契约
├── login-server/               服务实现 + 单测
├── login-prd/
│   ├── login-prd-client/
│   └── login-prd-bms/
├── login-orm/
│   ├── login-orm-api/
│   ├── login-orm-plus/
│   ├── login-orm-flex/
│   └── login-orm-hibernate/
├── login-remote/
│   ├── login-cloud-api/
│   ├── login-cloud-provider/
│   ├── login-dubbo-api/
│   └── login-dubbo-provider/
└── doc/
    └── sql/
```
