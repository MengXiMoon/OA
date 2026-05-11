# OA办公自动化系统

基于 SpringBoot 3 + Vue 3 + Element Plus + PostgreSQL 的办公自动化系统。

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

- **管理员**：所有功能
- **部门经理**：管理本部门事务，审批下属
- **普通员工**：个人办公操作

## 项目结构

```
OA/
├── oa-server/    # 后端
└── oa-web/       # 前端
```

## 快速启动

### 后端

```bash
cd oa-server
# 配置 application.yml 中的数据库连接
mvn spring-boot:run
```

### 前端

```bash
cd oa-web
npm install
npm run dev
```

默认管理员: `admin` / `123456`
