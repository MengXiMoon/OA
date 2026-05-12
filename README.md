# OA办公自动化系统

基于 SpringBoot 3 + Vue 3 + Element Plus + PostgreSQL 的办公自动化系统，陶瓷素白主题。

## 功能模块

| 模块 | 功能 |
|---|---|
| 信息发布 | 公告栏、公司活动、项目进度、公司周报 |
| 文件管理 | 收文管理、发文管理、档案管理 |
| 工作任务 | 个人任务查看、新建任务、分配下属任务 |
| 审批考勤 | 请假/出差申请与审批、出勤记录查看 |
| 工作日志 | 每日日志填写与查询 |
| 会议管理 | 会议室管理、会议预约与通知 |
| 系统管理 | 部门管理、用户管理、权限管理 |

## 技术栈

| 层 | 技术 |
|---|---|
| 后端 | SpringBoot 3.2、MyBatis-Plus 3.5、PostgreSQL、Spring Security + JWT |
| 前端 | Vue 3、Element Plus、Vite、Axios、Pinia、Vue Router |
| 接口文档 | Knife4j (Swagger) |

## 角色权限

- **管理员**：所有功能，系统配置、用户权限管理
- **部门经理**：管理本部门事务，审批下属申请，分配任务
- **普通员工**：个人办公操作、提交申请、填写日志

## 项目结构

```
OA/
├── oa-server/                          # 后端
│   └── src/main/resources/
│       └── db/migration/
│           ├── V1__init.sql            # 数据库建表
│           └── V2__company_data.sql    # 306条测试数据
└── oa-web/                             # 前端
    └── src/
        ├── styles/theme.css            # 陶瓷素白全局主题
        ├── views/                      # 页面组件
        ├── components/common/          # 通用组件
        ├── api/                        # 接口请求
        └── router/                     # 路由配置
```

## 快速启动

### 环境要求

- JDK 17+
- Maven 3.6+
- Node.js 18+
- PostgreSQL 15+

### 1. 初始化数据库

```sql
CREATE DATABASE oa_db;
```

### 2. 启动后端

```bash
cd oa-server
# 确认 application.yml 中数据库连接信息正确
mvn spring-boot:run
```

首次启动后，需要手动执行测试数据脚本：

```bash
psql -U postgres -d oa_db -f oa-server/src/main/resources/db/migration/V2__company_data.sql
```

### 3. 启动前端

```bash
cd oa-web
npm install
npm run dev
```

访问 http://localhost:5173

### 测试账号

| 账号 | 密码 | 角色 | 姓名 |
|---|---|---|---|
| admin | 123456 | 管理员 | 系统管理员 |
| zhangsan | 123456 | 部门经理 | 张三 |
| lisi | 123456 | 普通员工 | 李四 |
| wangwu | 123456 | 部门经理 | 王五 |

共有 **25 个测试用户**，涵盖 8 个部门。

### 测试数据

数据库包含 **306 条**公司生态数据：

| 表 | 数量 |
|---|---|
| 用户 | 25 |
| 部门 | 8 |
| 公告 | 20 |
| 公司活动 | 12 |
| 项目进度 | 8 |
| 周报 | 12 |
| 工作任务 | 46 |
| 请假记录 | 20 |
| 出差记录 | 15 |
| 考勤记录 | 60（5天×12人） |
| 工作日志 | 37 |
| 会议 | 24 |
| 收发文/档案 | 34 |
