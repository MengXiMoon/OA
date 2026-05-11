-- 部门表
CREATE TABLE sys_department (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    leader_id BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户表
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    dept_id BIGINT,
    role VARCHAR(20) NOT NULL DEFAULT 'employee',
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 公告表
CREATE TABLE oa_notice (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    publisher_id BIGINT,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 公司活动表
CREATE TABLE oa_company_event (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    cover_image VARCHAR(255),
    event_date DATE,
    location VARCHAR(200),
    publisher_id BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 项目进度表
CREATE TABLE oa_project_progress (
    id BIGSERIAL PRIMARY KEY,
    project_name VARCHAR(200) NOT NULL,
    content TEXT,
    progress_percent INT DEFAULT 0,
    responsible_id BIGINT,
    status VARCHAR(20) DEFAULT 'in_progress',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 公司周报表
CREATE TABLE oa_weekly_report (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    dept_id BIGINT,
    reporter_id BIGINT,
    report_week VARCHAR(20),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 收文表
CREATE TABLE oa_incoming_file (
    id BIGSERIAL PRIMARY KEY,
    file_no VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    send_org VARCHAR(200),
    receive_date DATE,
    file_type VARCHAR(50),
    content TEXT,
    attachment_url VARCHAR(255),
    handler_id BIGINT,
    status VARCHAR(20) DEFAULT 'pending',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 发文表
CREATE TABLE oa_outgoing_file (
    id BIGSERIAL PRIMARY KEY,
    file_no VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    send_to_org VARCHAR(200),
    send_date DATE,
    file_type VARCHAR(50),
    content TEXT,
    attachment_url VARCHAR(255),
    drafter_id BIGINT,
    status VARCHAR(20) DEFAULT 'draft',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 档案表
CREATE TABLE oa_file_archive (
    id BIGSERIAL PRIMARY KEY,
    file_no VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    file_type VARCHAR(50),
    keywords VARCHAR(255),
    archive_date DATE,
    file_url VARCHAR(255),
    dept_id BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 工作任务表
CREATE TABLE oa_task (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    creator_id BIGINT,
    assignee_id BIGINT,
    deadline TIMESTAMP,
    priority VARCHAR(20) DEFAULT 'normal',
    status VARCHAR(20) DEFAULT 'pending',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 请假表
CREATE TABLE oa_leave (
    id BIGSERIAL PRIMARY KEY,
    applicant_id BIGINT,
    leave_type VARCHAR(20) NOT NULL,
    reason TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    approver_id BIGINT,
    status VARCHAR(20) DEFAULT 'pending',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 出差表
CREATE TABLE oa_travel (
    id BIGSERIAL PRIMARY KEY,
    applicant_id BIGINT,
    destination VARCHAR(200) NOT NULL,
    reason TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    approver_id BIGINT,
    status VARCHAR(20) DEFAULT 'pending',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 考勤表
CREATE TABLE oa_attendance (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    record_date DATE NOT NULL,
    sign_in_time TIMESTAMP,
    sign_out_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'normal'
);

-- 工作日志表
CREATE TABLE oa_work_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    log_date DATE NOT NULL,
    today_content TEXT,
    tomorrow_plan TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 会议室表
CREATE TABLE oa_meeting_room (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(200),
    capacity INT DEFAULT 10,
    has_projector INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'available'
);

-- 会议表
CREATE TABLE oa_meeting (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    room_id BIGINT,
    organizer_id BIGINT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'scheduled',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 初始数据：管理员 (password: admin123)
INSERT INTO sys_user (username, password, real_name, role, status)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '系统管理员', 'admin', 1);
