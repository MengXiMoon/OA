# OA办公自动化系统设计文档

> 日期：2026-05-11 | 类型：学校项目 | 状态：已确认

## 1. 概述

基于 SpringBoot + Vue3 + ElementPlus 的办公自动化系统，覆盖信息发布、文件管理、行政办公、审批考勤、日志、会议管理、系统管理 7 大模块。

## 2. 技术栈

| 层 | 技术 | 版本 |
|---|---|---|
| 后端框架 | SpringBoot | 3.x |
| ORM | MyBatis-Plus | 3.5+ |
| 数据库 | PostgreSQL | 16 |
| 认证 | Spring Security + JWT | - |
| API文档 | Knife4j (Swagger) | 4.x |
| 前端框架 | Vue | 3.x |
| UI组件库 | Element Plus | 2.x |
| 构建工具 | Vite | 5.x |
| HTTP客户端 | Axios | - |
| 路由 | Vue Router | 4.x |
| 状态管理 | Pinia | - |

## 3. 项目结构

```
IdeaProjects/
└── OA/
    ├── oa-server/                 # 后端 SpringBoot
    │   ├── src/main/java/com/oa/
    │   │   ├── config/            # Security, JWT, CORS 配置
    │   │   ├── controller/        # 按模块分包
    │   │   │   ├── auth/          # 认证
    │   │   │   ├── notice/        # 信息发布
    │   │   │   ├── file/          # 文件管理
    │   │   │   ├── task/          # 工作任务
    │   │   │   ├── approval/      # 审批考勤
    │   │   │   ├── log/           # 工作日志
    │   │   │   ├── meeting/       # 会议管理
    │   │   │   └── system/        # 系统管理
    │   │   ├── service/           # 业务逻辑层
    │   │   ├── mapper/            # MyBatis-Plus Mapper
    │   │   ├── entity/            # 数据库实体
    │   │   ├── dto/               # 请求/响应 DTO
    │   │   ├── common/            # 统一返回结果、异常处理、常量
    │   │   └── security/          # JWT 工具、认证过滤器
    │   └── src/main/resources/
    │       ├── application.yml
    │       └── db/migration/      # SQL 初始化脚本
    │
    └── oa-web/                    # 前端 Vue
        ├── src/
        │   ├── views/             # 页面组件 (按模块分目录)
        │   ├── components/        # 通用组件
        │   ├── router/            # 路由配置
        │   ├── stores/            # Pinia 状态管理
        │   ├── api/               # Axios 请求封装
        │   └── utils/             # 工具函数
        └── package.json
```

## 4. 角色权限（3级）

| 角色 | 权限范围 |
|---|---|
| **管理员** (admin) | 系统全部功能，可管理所有部门数据 |
| **部门经理** (manager) | 管理本部门，审批下属请假/出差，分配任务，查看本部门出勤/日志 |
| **普通员工** (employee) | 操作自己的数据，申请请假/出差，写日志，查看公告/会议室 |

### 权限矩阵

| 功能 | 员工 | 经理 | 管理员 |
|---|---|---|---|
| 查看公告/活动/周报/项目进度 | ✅ | ✅ | ✅ |
| 发布公告/活动/周报 | ❌ | ❌ | ✅ |
| 收文/发文/档案管理 | ❌ | 本部门 | 全部 |
| 查看自己的任务 | ✅ | ✅ | ✅ |
| 新建任务 | ✅ | ✅ | ✅ |
| 分配任务给下属 | ❌ | ✅ | ✅ |
| 申请请假/出差 | ✅ | ✅ | ✅ |
| 查看自己出勤 | ✅ | ✅ | ✅ |
| 批准下属请假/出差 | ❌ | ✅ | ✅ |
| 查看员工状态 | ❌ | 本部门 | 全部 |
| 写工作日志 | ✅ | ✅ | ✅ |
| 查看自己日志 | ✅ | ✅ | ✅ |
| 查看他人日志 | ❌ | 本部门 | 全部 |
| 查看/预约会议室 | ✅ | ✅ | ✅ |
| 通知员工开会 | ❌ | ✅ | ✅ |
| 管理会议室 | ❌ | ❌ | ✅ |
| 查看全员出勤/日志 | ❌ | ❌ | ✅ |
| 系统权限管理 | ❌ | ❌ | ✅ |

## 5. 数据库设计（16张表）

### 基础模块（6表）

| 表名 | 说明 | 核心字段 |
|---|---|---|
| `sys_department` | 部门 | id, name, parent_id, leader_id, create_time |
| `sys_user` | 用户 | id, username, password, real_name, dept_id, role(admin/manager/employee), phone, email, avatar, status, create_time |
| `oa_notice` | 公告 | id, title, content, publisher_id, is_top, status, create_time |
| `oa_company_event` | 公司活动 | id, title, content, cover_image, event_date, location, publisher_id, create_time |
| `oa_project_progress` | 项目进度 | id, project_name, content, progress_percent, responsible_id, status, create_time |
| `oa_weekly_report` | 公司周报 | id, title, content, dept_id, reporter_id, report_week, create_time |

### 文件管理（3表）

| 表名 | 说明 | 核心字段 |
|---|---|---|
| `oa_incoming_file` | 收文 | id, file_no, title, send_org, receive_date, file_type, content, attachment_url, handler_id, status, create_time |
| `oa_outgoing_file` | 发文 | id, file_no, title, send_to_org, send_date, file_type, content, attachment_url, drafter_id, status, create_time |
| `oa_file_archive` | 档案 | id, file_no, title, file_type, keywords, archive_date, file_url, dept_id, create_time |

### 行政办公（1表）

| 表名 | 说明 | 核心字段 |
|---|---|---|
| `oa_task` | 工作任务 | id, title, content, creator_id, assignee_id, deadline, priority, status(pending/doing/done), create_time |

### 审批考勤（3表）

| 表名 | 说明 | 核心字段 |
|---|---|---|
| `oa_leave` | 请假申请 | id, applicant_id, leave_type(事假/病假/年假), reason, start_time, end_time, approver_id, status(pending/approved/rejected), create_time |
| `oa_travel` | 出差申请 | id, applicant_id, destination, reason, start_time, end_time, approver_id, status, create_time |
| `oa_attendance` | 考勤记录 | id, user_id, record_date, sign_in_time, sign_out_time, status(normal/late/early/absent) |

### 日志（1表）

| 表名 | 说明 | 核心字段 |
|---|---|---|
| `oa_work_log` | 工作日志 | id, user_id, log_date, today_content, tomorrow_plan, create_time |

### 会议管理（2表）

| 表名 | 说明 | 核心字段 |
|---|---|---|
| `oa_meeting_room` | 会议室 | id, name, location, capacity, has_projector, status(available/in_use/maintenance) |
| `oa_meeting` | 会议 | id, title, content, room_id, organizer_id, start_time, end_time, status, create_time |

## 6. API 模块划分

| 模块 | Controller | 核心接口 |
|---|---|---|
| 认证 | `AuthController` | POST /api/auth/login, POST /api/auth/register, GET /api/auth/userinfo |
| 信息发布 | `NoticeController` | 公告/活动/周报/项目进度的 CRUD + 分页查询 |
| 文件管理 | `IncomingFileController` | 收文 CRUD + 按状态/类型筛选 |
| | `OutgoingFileController` | 发文 CRUD + 按状态/类型筛选 |
| | `FileArchiveController` | 档案检索 + 分类查询 |
| 工作任务 | `TaskController` | 个人任务查询、创建任务、分配下属、修改截止时间 |
| 审批考勤 | `LeaveController` | 请假申请、审批（同意/拒绝） |
| | `TravelController` | 出差申请、审批 |
| | `AttendanceController` | 个人出勤查询、管理员查看全员出勤 |
| 工作日志 | `WorkLogController` | 填写日志、日志查询（按日期/人员） |
| 会议管理 | `MeetingRoomController` | 会议室 CRUD + 状态管理 |
| | `MeetingController` | 会议预约、会议通知、查看占用情况 |
| 系统管理 | `SystemController` | 部门管理、用户管理、角色分配、数据统计 |

## 7. 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

分页查询响应 data 中包含 `records`, `total`, `page`, `pageSize`。

## 8. 认证方案

- Spring Security + JWT 无状态认证
- 登录返回 token，前端存入 localStorage
- 每次请求通过 Authorization: Bearer <token> 携带
- 后端通过 SecurityContext 获取当前用户及角色
- 权限控制：`@PreAuthorize` 注解 + 方法内部门数据范围判断
