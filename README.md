# OA 办公自动化系统

基于 Spring Boot 3 + Vue 3 + Element Plus + PostgreSQL 的全栈办公自动化系统。

> **毕业设计项目** · 前后端分离 · JWT 认证 · RBAC 权限 · Docker 部署 · AI 助手

## 功能模块

| 模块 | 功能 |
|------|------|
| 信息发布 | 公告栏、公司活动、项目进度、公司周报 |
| 文件管理 | 收文管理、发文管理、档案管理 |
| 工作任务 | 我的任务、分配任务、任务状态跟踪 |
| 审批申请 | 请假申请、出差申请、出勤记录 |
| 工作日志 | 每日日志填写、历史查询 |
| 会议管理 | 会议室管理、会议预约 |
| 系统管理 | 用户管理、部门管理、角色权限 |
| **AI 助手** | 周报摘要生成、任务优先级建议（支持通义千问/DeepSeek） |

## 技术栈

| 层 | 技术 |
|----|------|
| 后端 | Spring Boot 3.2.5、MyBatis-Plus 3.5.6、Spring Security、JWT (jjwt 0.12) |
| 数据库 | PostgreSQL 15、Redis 7（缓存） |
| 消息队列 | RabbitMQ（异步通知） |
| 前端 | Vue 3.5、Element Plus 2.14、Vite 8、Pinia、Vue Router、Axios |
| AI | 通义千问 DashScope / DeepSeek / OpenAI 兼容 API |
| 部署 | Docker Compose 一键编排、Nginx 反向代理 |
| 文档 | Knife4j (Swagger) API 文档 |

## 角色权限

| 角色 | 权限范围 |
|------|----------|
| 管理员 | 全部功能，系统配置，用户/部门管理 |
| 部门经理 | 管理本部门事务，审批申请，分配任务 |
| 普通员工 | 个人办公，提交申请，填写日志 |

## 快速启动

### 环境要求

- JDK 17+ / Maven 3.6+ / Node.js 18+ / PostgreSQL 15+ / Redis 7+

### 本地开发

```bash
# 1. 创建数据库
psql -U postgres -c "CREATE DATABASE oa_db;"

# 2. 导入数据
psql -U postgres -d oa_db -f oa-server/src/main/resources/db/migration/V1__init.sql
psql -U postgres -d oa_db -f oa-server/src/main/resources/db/migration/V2__company_data.sql

# 3. 启动后端（端口 8080）
cd oa-server && mvn spring-boot:run

# 4. 启动前端（端口 5173）
cd oa-web && npm install && npm run dev
```

访问 http://localhost:5173

### Docker 部署

```bash
# 一键启动（首次构建约 5 分钟）
docker compose up -d --build

# 导入数据
docker exec -i oa-postgres psql -U oa_user -d oa_db < oa-server/src/main/resources/db/migration/V1__init.sql
docker exec -i oa-postgres psql -U oa_user -d oa_db < oa-server/src/main/resources/db/migration/V2__company_data.sql

# 访问 http://服务器IP
```

Docker Compose 包含 5 个服务：PostgreSQL · Redis · RabbitMQ · 后端 · 前端 (Nginx)

## 测试账号

| 账号 | 密码 | 角色 | 姓名 |
|------|------|------|------|
| admin | admin123 | 管理员 | 系统管理员 |
| zhangsan | admin123 | 部门经理 | 张三 |
| lisi | admin123 | 普通员工 | 李四 |

> 共 25 个测试用户，8 个部门，306 条测试数据

## AI 助手配置

在 `application.yml` 中配置 AI 参数：

```yaml
ai:
  endpoint: https://api.deepseek.com/chat/completions    # 或通义千问 dashscope 地址
  api-key: 你的API-Key
  model: deepseek-chat                                     # 或 qwen-plus
  timeout: 60
```

AI 功能会自动拉取当前用户的工作日志和任务数据，生成个性化内容。

## 项目结构

```
OA/
├── docker-compose.yml           # Docker 服务编排
├── oa-server/                   # 后端 Spring Boot
│   ├── Dockerfile
│   └── src/main/
│       ├── java/com/oa/
│       │   ├── controller/      # 16 个控制器（56 个 API）
│       │   ├── service/         # 业务逻辑 + RBAC + AI + 消息队列
│       │   ├── mapper/          # MyBatis-Plus 数据访问
│       │   ├── entity/          # 16 个实体
│       │   ├── config/          # Security / JWT / Redis / RabbitMQ / AI
│       │   ├── security/        # JWT 工具 / 认证过滤器
│       │   └── common/          # 统一响应 / 异常处理
│       └── resources/
│           ├── application.yml
│           └── db/migration/    # 数据库脚本（16 张表 + 测试数据）
└── oa-web/                      # 前端 Vue 3
    ├── Dockerfile
    ├── nginx.conf
    └── src/
        ├── views/               # 17 个页面
        ├── components/          # 公共组件
        ├── api/                 # API 层（9 个模块）
        ├── router/              # 路由 + 守卫
        ├── stores/              # Pinia 状态管理
        └── styles/              # 全局主题
```

## API 文档

启动后端后访问 http://localhost:8080/doc.html 查看 Knife4j 接口文档。
