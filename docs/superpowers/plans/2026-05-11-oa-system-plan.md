# OA办公自动化系统 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a complete office automation system with 7 modules: info publishing, file management, task management, approval & attendance, work log, meeting management, and system administration.

**Architecture:** Monolithic SpringBoot 3 backend with layered architecture (controller/service/mapper/entity) + Vue 3 SPA frontend with Element Plus. JWT stateless auth with 3-level RBAC (admin/manager/employee). PostgreSQL for persistence, MyBatis-Plus for ORM.

**Tech Stack:** SpringBoot 3.x, MyBatis-Plus 3.5+, PostgreSQL 16, Spring Security + JWT, Vue 3, Element Plus 2.x, Vite 5.x, Axios, Vue Router 4, Pinia

---

## File Structure

```
OA/
├── oa-server/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/oa/
│       │   ├── OaApplication.java
│       │   ├── config/
│       │   │   ├── SecurityConfig.java
│       │   │   ├── JwtAuthFilter.java
│       │   │   └── CorsConfig.java
│       │   ├── security/
│       │   │   ├── JwtUtils.java
│       │   │   ├── UserDetailsServiceImpl.java
│       │   │   └── LoginUser.java
│       │   ├── common/
│       │   │   ├── Result.java
│       │   │   ├── PageResult.java
│       │   │   ├── BusinessException.java
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── entity/
│       │   │   ├── SysDepartment.java
│       │   │   ├── SysUser.java
│       │   │   ├── OaNotice.java
│       │   │   ├── OaCompanyEvent.java
│       │   │   ├── OaProjectProgress.java
│       │   │   ├── OaWeeklyReport.java
│       │   │   ├── OaIncomingFile.java
│       │   │   ├── OaOutgoingFile.java
│       │   │   ├── OaFileArchive.java
│       │   │   ├── OaTask.java
│       │   │   ├── OaLeave.java
│       │   │   ├── OaTravel.java
│       │   │   ├── OaAttendance.java
│       │   │   ├── OaWorkLog.java
│       │   │   ├── OaMeetingRoom.java
│       │   │   └── OaMeeting.java
│       │   ├── dto/
│       │   │   ├── LoginRequest.java
│       │   │   ├── LoginResponse.java
│       │   │   ├── PageQuery.java
│       │   │   └── (module-specific DTOs)
│       │   ├── mapper/
│       │   │   ├── SysDepartmentMapper.java
│       │   │   ├── SysUserMapper.java
│       │   │   ├── OaNoticeMapper.java
│       │   │   ├── OaCompanyEventMapper.java
│       │   │   ├── OaProjectProgressMapper.java
│       │   │   ├── OaWeeklyReportMapper.java
│       │   │   ├── OaIncomingFileMapper.java
│       │   │   ├── OaOutgoingFileMapper.java
│       │   │   ├── OaFileArchiveMapper.java
│       │   │   ├── OaTaskMapper.java
│       │   │   ├── OaLeaveMapper.java
│       │   │   ├── OaTravelMapper.java
│       │   │   ├── OaAttendanceMapper.java
│       │   │   ├── OaWorkLogMapper.java
│       │   │   ├── OaMeetingRoomMapper.java
│       │   │   └── OaMeetingMapper.java
│       │   ├── service/
│       │   │   ├── SysDepartmentService.java
│       │   │   ├── SysUserService.java
│       │   │   ├── OaNoticeService.java
│       │   │   ├── OaCompanyEventService.java
│       │   │   ├── OaProjectProgressService.java
│       │   │   ├── OaWeeklyReportService.java
│       │   │   ├── OaIncomingFileService.java
│       │   │   ├── OaOutgoingFileService.java
│       │   │   ├── OaFileArchiveService.java
│       │   │   ├── OaTaskService.java
│       │   │   ├── OaLeaveService.java
│       │   │   ├── OaTravelService.java
│       │   │   ├── OaAttendanceService.java
│       │   │   ├── OaWorkLogService.java
│       │   │   ├── OaMeetingRoomService.java
│       │   │   └── OaMeetingService.java
│       │   └── controller/
│       │       ├── auth/AuthController.java
│       │       ├── notice/NoticeController.java
│       │       ├── notice/CompanyEventController.java
│       │       ├── notice/ProjectProgressController.java
│       │       ├── notice/WeeklyReportController.java
│       │       ├── file/IncomingFileController.java
│       │       ├── file/OutgoingFileController.java
│       │       ├── file/FileArchiveController.java
│       │       ├── task/TaskController.java
│       │       ├── approval/LeaveController.java
│       │       ├── approval/TravelController.java
│       │       ├── approval/AttendanceController.java
│       │       ├── log/WorkLogController.java
│       │       ├── meeting/MeetingRoomController.java
│       │       ├── meeting/MeetingController.java
│       │       └── system/SystemController.java
│       └── resources/
│           ├── application.yml
│           └── db/migration/V1__init.sql
│
└── oa-web/
    ├── package.json
    ├── vite.config.js
    ├── index.html
    └── src/
        ├── main.js
        ├── App.vue
        ├── router/index.js
        ├── stores/
        │   ├── user.js
        │   └── app.js
        ├── api/
        │   ├── request.js
        │   ├── auth.js
        │   ├── notice.js
        │   ├── file.js
        │   ├── task.js
        │   ├── approval.js
        │   ├── log.js
        │   ├── meeting.js
        │   └── system.js
        ├── utils/
        │   └── auth.js
        ├── views/
        │   ├── login/Login.vue
        │   ├── layout/Layout.vue
        │   ├── dashboard/Dashboard.vue
        │   ├── notice/
        │   │   ├── NoticeList.vue
        │   │   ├── CompanyEventList.vue
        │   │   ├── ProjectProgressList.vue
        │   │   └── WeeklyReportList.vue
        │   ├── file/
        │   │   ├── IncomingFileList.vue
        │   │   ├── OutgoingFileList.vue
        │   │   └── FileArchiveList.vue
        │   ├── task/
        │   │   └── TaskList.vue
        │   ├── approval/
        │   │   ├── LeaveList.vue
        │   │   ├── TravelList.vue
        │   │   └── AttendanceList.vue
        │   ├── log/
        │   │   └── WorkLogList.vue
        │   ├── meeting/
        │   │   ├── MeetingRoomList.vue
        │   │   └── MeetingList.vue
        │   └── system/
        │       ├── DepartmentList.vue
        │       └── UserList.vue
        └── components/
            └── common/
                ├── Sidebar.vue
                ├── Navbar.vue
                └── Pagination.vue
```

---

## Phase 1: Project Scaffolding

### Task 1: Initialize Backend SpringBoot Project

**Files:**
- Create: `OA/oa-server/pom.xml`
- Create: `OA/oa-server/src/main/java/com/oa/OaApplication.java`
- Create: `OA/oa-server/src/main/resources/application.yml`

- [ ] **Step 1: Create pom.xml with all dependencies**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/>
    </parent>
    <groupId>com.oa</groupId>
    <artifactId>oa-server</artifactId>
    <version>1.0.0</version>
    <name>oa-server</name>

    <properties>
        <java.version>17</java.version>
        <mybatis-plus.version>3.5.6</mybatis-plus.version>
        <knife4j.version>4.3.0</knife4j.version>
        <jjwt.version>0.12.5</jjwt.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
            <version>${knife4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jjwt.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

- [ ] **Step 2: Create SpringBoot main class**

```java
package com.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oa.mapper")
public class OaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OaApplication.class, args);
    }
}
```

- [ ] **Step 3: Create application.yml**

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/oa_db
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Shanghai

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

knife4j:
  enable: true

jwt:
  secret: oa-system-secret-key-must-be-at-least-256-bits-long-for-hs256
  expiration: 86400000
```

- [ ] **Step 4: Build and verify project compiles**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 5: Commit**

```bash
cd OA && git init && git add oa-server/ && git commit -m "feat: initialize SpringBoot project with dependencies"
```

---

### Task 2: Initialize Frontend Vue Project

**Files:**
- Create: `OA/oa-web/` (via vite scaffolding)

- [ ] **Step 1: Scaffold Vue project with Vite**

Run: `cd OA && npm create vite@latest oa-web -- --template vue`
Expected: Project scaffolded in OA/oa-web

- [ ] **Step 2: Install dependencies**

Run:
```bash
cd OA/oa-web && npm install && npm install element-plus @element-plus/icons-vue vue-router@4 pinia axios
```
Expected: All packages installed

- [ ] **Step 3: Configure vite.config.js**

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
```

- [ ] **Step 4: Verify dev server starts**

Run: `cd OA/oa-web && npm run dev`
Expected: Vite dev server starts on port 3000
Then stop with Ctrl+C

- [ ] **Step 5: Commit**

```bash
git add oa-web/ && git commit -m "feat: initialize Vue3 frontend project"
```

---

## Phase 2: Common Infrastructure

### Task 3: Create Unified Response Classes

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/common/Result.java`
- Create: `OA/oa-server/src/main/java/com/oa/common/PageResult.java`
- Create: `OA/oa-server/src/main/java/com/oa/common/BusinessException.java`
- Create: `OA/oa-server/src/main/java/com/oa/common/GlobalExceptionHandler.java`
- Test: `OA/oa-server/src/test/java/com/oa/common/ResultTest.java`

- [ ] **Step 1: Write test for Result**

```java
package com.oa.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void shouldCreateSuccessResult() {
        Result<String> result = Result.success("hello");
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals("hello", result.getData());
    }

    @Test
    void shouldCreateErrorResult() {
        Result<Void> result = Result.error(500, "server error");
        assertEquals(500, result.getCode());
        assertEquals("server error", result.getMessage());
        assertNull(result.getData());
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `cd OA/oa-server && mvn test -Dtest=ResultTest -q`
Expected: Compilation error (class not found)

- [ ] **Step 3: Create Result.java**

```java
package com.oa.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
}
```

- [ ] **Step 4: Run test to verify it passes**

Run: `cd OA/oa-server && mvn test -Dtest=ResultTest -q`
Expected: Tests pass

- [ ] **Step 5: Create PageResult.java**

```java
package com.oa.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    private List<T> records;
    private long total;
    private long page;
    private long pageSize;
}
```

- [ ] **Step 6: Create BusinessException.java**

```java
package com.oa.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        this(500, message);
    }
}
```

- [ ] **Step 7: Create GlobalExceptionHandler.java**

```java
package com.oa.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleException(Exception e) {
        return Result.error(500, "服务器内部错误");
    }
}
```

- [ ] **Step 8: Commit**

```bash
git add oa-server/src/main/java/com/oa/common/ oa-server/src/test/
git commit -m "feat: add unified response and exception handling"
```

---

### Task 4: Create JWT Security Infrastructure

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/security/JwtUtils.java`
- Create: `OA/oa-server/src/main/java/com/oa/security/LoginUser.java`
- Create: `OA/oa-server/src/main/java/com/oa/security/UserDetailsServiceImpl.java`
- Create: `OA/oa-server/src/main/java/com/oa/config/JwtAuthFilter.java`
- Create: `OA/oa-server/src/main/java/com/oa/config/SecurityConfig.java`
- Create: `OA/oa-server/src/main/java/com/oa/config/CorsConfig.java`
- Create: `OA/oa-server/src/main/java/com/oa/entity/SysUser.java`
- Create: `OA/oa-server/src/main/java/com/oa/entity/SysDepartment.java`
- Create: `OA/oa-server/src/main/java/com/oa/mapper/SysUserMapper.java`
- Create: `OA/oa-server/src/main/java/com/oa/mapper/SysDepartmentMapper.java`

- [ ] **Step 1: Create entity classes**

Create `SysDepartment.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_department")
public class SysDepartment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private Long leaderId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `SysUser.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String realName;
    private Long deptId;
    private String role;
    private String phone;
    private String email;
    private String avatar;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
```

- [ ] **Step 2: Create Mapper interfaces**

```java
package com.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
```

```java
package com.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.entity.SysDepartment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {
}
```

- [ ] **Step 3: Create JwtUtils.java**

```java
package com.oa.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey key;
    private final long expiration;

    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(String username, Long userId, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
```

- [ ] **Step 4: Create LoginUser.java**

```java
package com.oa.security;

import com.oa.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private SysUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
    }

    @Override
    public String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getUsername(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.getStatus() == 1; }
}
```

- [ ] **Step 5: Create UserDetailsServiceImpl.java**

```java
package com.oa.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oa.entity.SysUser;
import com.oa.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username)
        );
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUser(user);
    }
}
```

- [ ] **Step 6: Create JwtAuthFilter.java**

```java
package com.oa.config;

import com.oa.security.JwtUtils;
import com.oa.security.LoginUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtUtils.isTokenValid(token)) {
            Claims claims = jwtUtils.parseToken(token);
            LoginUser loginUser = new LoginUser(null);
            LoginUser principal = new LoginUser(null);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(claims, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
```

Wait — the filter above has a bug. `LoginUser` needs the actual user entity, not null. Let me fix this in the filter to store claims and let services fetch the full user when needed. Actually, for simplicity in a school project, let me store the minimal data directly.

Let me revise the approach. Instead of using LoginUser in the SecurityContext, I'll store a custom authentication that holds the JWT claims with userId, username, and role. Then services can extract these from the SecurityContext.

- [ ] **Step 6: Create JwtAuthFilter.java (revised)**

```java
package com.oa.config;

import com.oa.security.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtUtils.isTokenValid(token)) {
            Claims claims = jwtUtils.parseToken(token);
            String role = claims.get("role", String.class);
            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
            );
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(claims, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
```

- [ ] **Step 7: Create SecurityConfig.java**

```java
package com.oa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers("/doc.html", "/webjars/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

- [ ] **Step 8: Create CorsConfig.java**

```java
package com.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

- [ ] **Step 9: Build to verify compilation**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 10: Commit**

```bash
git add oa-server/src/main/java/com/oa/security/ oa-server/src/main/java/com/oa/config/ oa-server/src/main/java/com/oa/entity/SysUser.java oa-server/src/main/java/com/oa/entity/SysDepartment.java oa-server/src/main/java/com/oa/mapper/SysUserMapper.java oa-server/src/main/java/com/oa/mapper/SysDepartmentMapper.java
git commit -m "feat: add JWT authentication and Spring Security config"
```

---

## Phase 3: Database & Entities

### Task 5: Create Database Schema

**Files:**
- Create: `OA/oa-server/src/main/resources/db/migration/V1__init.sql`

- [ ] **Step 1: Write full schema SQL**

```sql
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

-- 初始数据：管理员
INSERT INTO sys_user (username, password, real_name, role, status)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '系统管理员', 'admin', 1);
```

- [ ] **Step 2: Build to check nothing is broken**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 3: Commit**

```bash
git add oa-server/src/main/resources/db/
git commit -m "feat: add database schema with 16 tables"
```

---

### Task 6: Create All Remaining Entity Classes

**Files:**
- Create: 14 entity files in `OA/oa-server/src/main/java/com/oa/entity/`

- [ ] **Step 1: Create all entities at once**

Create `OaNotice.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_notice")
public class OaNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private Integer isTop;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaCompanyEvent.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_company_event")
public class OaCompanyEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String coverImage;
    private LocalDate eventDate;
    private String location;
    private Long publisherId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaProjectProgress.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_project_progress")
public class OaProjectProgress {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String projectName;
    private String content;
    private Integer progressPercent;
    private Long responsibleId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaWeeklyReport.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_weekly_report")
public class OaWeeklyReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long deptId;
    private Long reporterId;
    private String reportWeek;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaIncomingFile.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_incoming_file")
public class OaIncomingFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileNo;
    private String title;
    private String sendOrg;
    private LocalDate receiveDate;
    private String fileType;
    private String content;
    private String attachmentUrl;
    private Long handlerId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaOutgoingFile.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_outgoing_file")
public class OaOutgoingFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileNo;
    private String title;
    private String sendToOrg;
    private LocalDate sendDate;
    private String fileType;
    private String content;
    private String attachmentUrl;
    private Long drafterId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaFileArchive.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_file_archive")
public class OaFileArchive {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileNo;
    private String title;
    private String fileType;
    private String keywords;
    private LocalDate archiveDate;
    private String fileUrl;
    private Long deptId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaTask.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_task")
public class OaTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long creatorId;
    private Long assigneeId;
    private LocalDateTime deadline;
    private String priority;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaLeave.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_leave")
public class OaLeave {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long applicantId;
    private String leaveType;
    private String reason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long approverId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaTravel.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_travel")
public class OaTravel {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long applicantId;
    private String destination;
    private String reason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long approverId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaAttendance.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_attendance")
public class OaAttendance {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDate recordDate;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
    private String status;
}
```

Create `OaWorkLog.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_work_log")
public class OaWorkLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDate logDate;
    private String todayContent;
    private String tomorrowPlan;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

Create `OaMeetingRoom.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("oa_meeting_room")
public class OaMeetingRoom {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer hasProjector;
    private String status;
}
```

Create `OaMeeting.java`:
```java
package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_meeting")
public class OaMeeting {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long roomId;
    private Long organizerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
```

- [ ] **Step 2: Build to verify**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 3: Commit**

```bash
git add oa-server/src/main/java/com/oa/entity/
git commit -m "feat: add all 16 entity classes"
```

---

### Task 7: Create All Mapper Interfaces

**Files:**
- Create: 14 mapper files in `OA/oa-server/src/main/java/com/oa/mapper/`

- [ ] **Step 1: Create all mapper interfaces**

Each mapper follows the same pattern — extend `BaseMapper<T>`:

```java
package com.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.entity.OaNotice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OaNoticeMapper extends BaseMapper<OaNotice> {
}
```

Create identical patterns for: `OaCompanyEventMapper`, `OaProjectProgressMapper`, `OaWeeklyReportMapper`, `OaIncomingFileMapper`, `OaOutgoingFileMapper`, `OaFileArchiveMapper`, `OaTaskMapper`, `OaLeaveMapper`, `OaTravelMapper`, `OaAttendanceMapper`, `OaWorkLogMapper`, `OaMeetingRoomMapper`, `OaMeetingMapper`.

- [ ] **Step 2: Build to verify**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 3: Commit**

```bash
git add oa-server/src/main/java/com/oa/mapper/
git commit -m "feat: add all mapper interfaces"
```

---

## Phase 4: Authentication Module

### Task 8: Create Auth DTOs

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/dto/LoginRequest.java`
- Create: `OA/oa-server/src/main/java/com/oa/dto/LoginResponse.java`
- Create: `OA/oa-server/src/main/java/com/oa/dto/PageQuery.java`

- [ ] **Step 1: Create LoginRequest.java**

```java
package com.oa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
```

- [ ] **Step 2: Create LoginResponse.java**

```java
package com.oa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long userId;
    private String username;
    private String realName;
    private String role;
}
```

- [ ] **Step 3: Create PageQuery.java**

```java
package com.oa.dto;

import lombok.Data;

@Data
public class PageQuery {
    private long page = 1;
    private long pageSize = 10;
    private String keyword;
}
```

- [ ] **Step 4: Commit**

```bash
git add oa-server/src/main/java/com/oa/dto/
git commit -m "feat: add auth DTOs and page query"
```

---

### Task 9: Create AuthController and SysUserService

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/service/SysUserService.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/auth/AuthController.java`
- Create: `OA/oa-server/src/main/java/com/oa/common/SecurityUtils.java`
- Test: `OA/oa-server/src/test/java/com/oa/controller/auth/AuthControllerTest.java`

- [ ] **Step 1: Create SecurityUtils.java helper**

```java
package com.oa.common;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Claims getCurrentClaims() {
        return (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getCurrentClaims().get("userId", Long.class);
    }

    public static String getCurrentUsername() {
        return getCurrentClaims().getSubject();
    }

    public static String getCurrentRole() {
        return getCurrentClaims().get("role", String.class);
    }
}
```

- [ ] **Step 2: Create SysUserService.java**

```java
package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.BusinessException;
import com.oa.common.SecurityUtils;
import com.oa.entity.SysUser;
import com.oa.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private final PasswordEncoder passwordEncoder;

    public SysUser register(String username, String password, String realName) {
        if (lambdaQuery().eq(SysUser::getUsername, username).count() > 0) {
            throw new BusinessException("用户名已存在");
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRealName(realName);
        user.setRole("employee");
        user.setStatus(1);
        save(user);
        return user;
    }

    public List<SysUser> getUsersByDept(Long deptId) {
        return lambdaQuery().eq(SysUser::getDeptId, deptId).list();
    }

    public PageResult<SysUser> pageQuery(PageQuery query) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(SysUser::getUsername, query.getKeyword())
                   .or().like(SysUser::getRealName, query.getKeyword());
        }
        wrapper.orderByDesc(SysUser::getCreateTime);
        Page<SysUser> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public List<SysUser> getSubordinates() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        SysUser current = getById(currentUserId);
        return lambdaQuery().eq(SysUser::getDeptId, current.getDeptId()).list();
    }
}
```

- [ ] **Step 3: Create AuthController.java**

```java
package com.oa.controller.auth;

import com.oa.common.Result;
import com.oa.common.SecurityUtils;
import com.oa.dto.LoginRequest;
import com.oa.dto.LoginResponse;
import com.oa.entity.SysUser;
import com.oa.security.JwtUtils;
import com.oa.service.SysUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SysUserService userService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SysUser user = userService.lambdaQuery()
                .eq(SysUser::getUsername, request.getUsername()).one();
        String token = jwtUtils.generateToken(user.getUsername(), user.getId(), user.getRole());
        LoginResponse resp = new LoginResponse(token, user.getId(),
                user.getUsername(), user.getRealName(), user.getRole());
        return Result.success(resp);
    }

    @PostMapping("/register")
    public Result<LoginResponse> register(@RequestBody Map<String, String> body) {
        SysUser user = userService.register(
                body.get("username"), body.get("password"), body.get("realName"));
        String token = jwtUtils.generateToken(user.getUsername(), user.getId(), user.getRole());
        return Result.success(new LoginResponse(token, user.getId(),
                user.getUsername(), user.getRealName(), user.getRole()));
    }

    @GetMapping("/userinfo")
    public Result<Map<String, Object>> userinfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        SysUser user = userService.getById(userId);
        return Result.success(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "realName", user.getRealName(),
                "role", user.getRole(),
                "deptId", user.getDeptId() != null ? user.getDeptId() : 0
        ));
    }
}
```

- [ ] **Step 4: Build and verify**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 5: Commit**

```bash
git add oa-server/src/main/java/com/oa/service/ oa-server/src/main/java/com/oa/controller/ oa-server/src/main/java/com/oa/common/SecurityUtils.java
git commit -m "feat: implement auth login/register/userinfo endpoints"
```

---

## Phase 5: Info Publishing Module (公告/活动/项目进度/周报)

### Task 10: Create Notice Services and Controllers

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/service/OaNoticeService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaCompanyEventService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaProjectProgressService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaWeeklyReportService.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/notice/NoticeController.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/notice/CompanyEventController.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/notice/ProjectProgressController.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/notice/WeeklyReportController.java`

- [ ] **Step 1: Create OaNoticeService.java**

```java
package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaNotice;
import com.oa.mapper.OaNoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaNoticeService extends ServiceImpl<OaNoticeMapper, OaNotice> {

    public PageResult<OaNotice> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaNotice> wrapper = new LambdaQueryWrapper<OaNotice>()
                .orderByDesc(OaNotice::getIsTop)
                .orderByDesc(OaNotice::getCreateTime);
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(OaNotice::getTitle, query.getKeyword());
        }
        Page<OaNotice> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void publish(OaNotice notice) {
        notice.setPublisherId(SecurityUtils.getCurrentUserId());
        save(notice);
    }
}
```

- [ ] **Step 2: Create the other 3 services with identical pattern**

Each extends `ServiceImpl<Mapper, Entity>` with a `pageQuery(PageQuery)` method using keyword search on title, ordered by createTime desc.

- [ ] **Step 3: Create NoticeController.java**

```java
package com.oa.controller.notice;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaNotice;
import com.oa.service.OaNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final OaNoticeService noticeService;

    @GetMapping
    public Result<PageResult<OaNotice>> list(PageQuery query) {
        return Result.success(noticeService.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaNotice> detail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> create(@RequestBody OaNotice notice) {
        noticeService.publish(notice);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaNotice notice) {
        notice.setId(id);
        noticeService.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.success();
    }
}
```

- [ ] **Step 4: Create CompanyEventController.java** (same pattern, path: `/api/company-event`)

- [ ] **Step 5: Create ProjectProgressController.java** (path: `/api/project-progress`, admin can modify, all can view)

- [ ] **Step 6: Create WeeklyReportController.java** (path: `/api/weekly-report`)

- [ ] **Step 7: Build and verify**

Run: `cd OA/oa-server && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 8: Commit**

```bash
git add oa-server/src/main/java/com/oa/service/OaNoticeService.java oa-server/src/main/java/com/oa/service/OaCompanyEventService.java oa-server/src/main/java/com/oa/service/OaProjectProgressService.java oa-server/src/main/java/com/oa/service/OaWeeklyReportService.java oa-server/src/main/java/com/oa/controller/notice/
git commit -m "feat: add info publishing module (notice/event/progress/weekly)"
```

---

## Phase 6: File Management Module

### Task 11: Create File Services and Controllers

**Files:**
- Create: Service + Controller for IncomingFile, OutgoingFile, FileArchive (6 files)

- [ ] **Step 1: Create OaIncomingFileService.java**

```java
package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.BusinessException;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaIncomingFile;
import com.oa.mapper.OaIncomingFileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaIncomingFileService extends ServiceImpl<OaIncomingFileMapper, OaIncomingFile> {

    public PageResult<OaIncomingFile> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaIncomingFile> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            throw new BusinessException(403, "无权访问");
        }
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w.like(OaIncomingFile::getTitle, query.getKeyword())
                    .or().like(OaIncomingFile::getFileNo, query.getKeyword()));
        }
        wrapper.orderByDesc(OaIncomingFile::getCreateTime);
        Page<OaIncomingFile> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
```

- [ ] **Step 2: Create IncomingFileController.java**

```java
package com.oa.controller.file;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaIncomingFile;
import com.oa.service.OaIncomingFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incoming-file")
@RequiredArgsConstructor
public class IncomingFileController {

    private final OaIncomingFileService service;

    @GetMapping
    public Result<PageResult<OaIncomingFile>> list(PageQuery query) {
        return Result.success(service.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaIncomingFile> detail(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> create(@RequestBody OaIncomingFile file) {
        service.save(file);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaIncomingFile file) {
        file.setId(id);
        service.updateById(file);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success();
    }
}
```

- [ ] **Step 3: Create outgoing + archive** (same pattern, paths: `/api/outgoing-file`, `/api/file-archive`)

- [ ] **Step 4: Build and commit**

```bash
cd OA/oa-server && mvn compile -q && cd ../..
git add oa-server/src/main/java/com/oa/service/Oa*FileService.java oa-server/src/main/java/com/oa/service/OaFileArchiveService.java oa-server/src/main/java/com/oa/controller/file/
git commit -m "feat: add file management module"
```

---

## Phase 7: Task, Approval, Log, Meeting Modules

### Task 12: Task Module

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/service/OaTaskService.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/task/TaskController.java`

- [ ] **Step 1: Create OaTaskService.java**

```java
package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaTask;
import com.oa.mapper.OaTaskMapper;
import org.springframework.stereotype.Service;

@Service
public class OaTaskService extends ServiceImpl<OaTaskMapper, OaTask> {

    public PageResult<OaTask> getMyTasks(PageQuery query) {
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaTask> wrapper = new LambdaQueryWrapper<OaTask>()
                .eq(OaTask::getAssigneeId, userId)
                .orderByDesc(OaTask::getCreateTime);
        Page<OaTask> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public PageResult<OaTask> getAssignedTasks(PageQuery query) {
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaTask> wrapper = new LambdaQueryWrapper<OaTask>()
                .eq(OaTask::getCreatorId, userId)
                .orderByDesc(OaTask::getCreateTime);
        Page<OaTask> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void assignTask(OaTask task) {
        task.setCreatorId(SecurityUtils.getCurrentUserId());
        task.setStatus("pending");
        save(task);
    }
}
```

- [ ] **Step 2: Create TaskController.java**

```java
package com.oa.controller.task;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaTask;
import com.oa.service.OaTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final OaTaskService taskService;

    @GetMapping("/my")
    public Result<PageResult<OaTask>> myTasks(PageQuery query) {
        return Result.success(taskService.getMyTasks(query));
    }

    @GetMapping("/assigned")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<PageResult<OaTask>> assignedTasks(PageQuery query) {
        return Result.success(taskService.getAssignedTasks(query));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> create(@RequestBody OaTask task) {
        taskService.assignTask(task);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaTask task) {
        task.setId(id);
        taskService.updateById(task);
        return Result.success();
    }
}
```

- [ ] **Step 3: Build and commit**

```bash
cd OA/oa-server && mvn compile -q && cd ../..
git add oa-server/src/main/java/com/oa/service/OaTaskService.java oa-server/src/main/java/com/oa/controller/task/
git commit -m "feat: add task management module"
```

---

### Task 13: Approval & Attendance Module

**Files:**
- Create: Services and Controllers for Leave, Travel, Attendance (6 files)

- [ ] **Step 1: Create OaLeaveService.java** with paginated queries filtering by role (employee sees own, manager sees dept, admin sees all) + approval logic.

```java
package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaLeave;
import com.oa.mapper.OaLeaveMapper;
import org.springframework.stereotype.Service;

@Service
public class OaLeaveService extends ServiceImpl<OaLeaveMapper, OaLeave> {

    public PageResult<OaLeave> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaLeave> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            wrapper.eq(OaLeave::getApplicantId, userId);
        } else if ("manager".equals(role)) {
            // manager sees own + subordinates — simplified: own dept
            wrapper.eq(OaLeave::getApproverId, userId).or().eq(OaLeave::getApplicantId, userId);
        }
        wrapper.orderByDesc(OaLeave::getCreateTime);
        Page<OaLeave> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void approve(Long id, String status) {
        OaLeave leave = getById(id);
        leave.setStatus(status);
        leave.setApproverId(SecurityUtils.getCurrentUserId());
        updateById(leave);
    }
}
```

- [ ] **Step 2: Create LeaveController.java**

```java
package com.oa.controller.approval;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaLeave;
import com.oa.service.OaLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final OaLeaveService leaveService;

    @GetMapping
    public Result<PageResult<OaLeave>> list(PageQuery query) {
        return Result.success(leaveService.pageQuery(query));
    }

    @PostMapping
    public Result<Void> apply(@RequestBody OaLeave leave) {
        leaveService.save(leave);
        return Result.success();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> approve(@PathVariable Long id, @RequestBody Map<String, String> body) {
        leaveService.approve(id, body.get("status"));
        return Result.success();
    }
}
```

- [ ] **Step 3: Create OaTravelService + TravelController** (identical pattern, path `/api/travel`)

- [ ] **Step 4: Create OaAttendanceService** with employee view (own records) and admin view (all records)

- [ ] **Step 5: Create AttendanceController.java**

```java
package com.oa.controller.approval;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaAttendance;
import com.oa.service.OaAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final OaAttendanceService attendanceService;

    @GetMapping
    public Result<PageResult<OaAttendance>> list(PageQuery query) {
        return Result.success(attendanceService.pageQuery(query));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<PageResult<OaAttendance>> listAll(PageQuery query) {
        return Result.success(attendanceService.pageQueryAll(query));
    }
}
```

- [ ] **Step 6: Build and commit**

```bash
cd OA/oa-server && mvn compile -q && cd ../..
git add oa-server/src/main/java/com/oa/service/OaLeaveService.java oa-server/src/main/java/com/oa/service/OaTravelService.java oa-server/src/main/java/com/oa/service/OaAttendanceService.java oa-server/src/main/java/com/oa/controller/approval/
git commit -m "feat: add approval and attendance module"
```

---

### Task 14: Work Log & Meeting & System Modules

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/service/SysDepartmentService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaAttendanceService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaWorkLogService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaMeetingRoomService.java`
- Create: `OA/oa-server/src/main/java/com/oa/service/OaMeetingService.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/log/WorkLogController.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/meeting/MeetingRoomController.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/meeting/MeetingController.java`
- Create: `OA/oa-server/src/main/java/com/oa/controller/system/SystemController.java`

- [ ] **Step 1: Create OaWorkLogService + WorkLogController** (path `/api/work-log`)
  - Employee writes own log, queries own log
  - Manager/Amin can query others' by userId filter

- [ ] **Step 2: Create OaMeetingRoomService + MeetingRoomController** (path `/api/meeting-room`)
  - CRUD, status management (admin only for create/delete)
  - All can view

- [ ] **Step 3: Create OaMeetingService + MeetingController** (path `/api/meeting`)
  - Check room availability (query by room + time range)
  - Schedule meeting, view meetings

- [ ] **Step 4: Create SysDepartmentService.java**

```java
package com.oa.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.entity.SysDepartment;
import com.oa.mapper.SysDepartmentMapper;
import org.springframework.stereotype.Service;

@Service
public class SysDepartmentService extends ServiceImpl<SysDepartmentMapper, SysDepartment> {
}
```

- [ ] **Step 5: Create OaAttendanceService.java**

```java
package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaAttendance;
import com.oa.mapper.OaAttendanceMapper;
import org.springframework.stereotype.Service;

@Service
public class OaAttendanceService extends ServiceImpl<OaAttendanceMapper, OaAttendance> {

    public PageResult<OaAttendance> pageQuery(PageQuery query) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<OaAttendance> page = page(new Page<>(query.getPage(), query.getPageSize()),
                new LambdaQueryWrapper<OaAttendance>().eq(OaAttendance::getUserId, userId));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public PageResult<OaAttendance> pageQueryAll(PageQuery query) {
        Page<OaAttendance> page = page(new Page<>(query.getPage(), query.getPageSize()));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
```

- [ ] **Step 6: Create SystemController** (path `/api/system`) for department and user management

```java
package com.oa.controller.system;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.SysDepartment;
import com.oa.entity.SysUser;
import com.oa.service.SysDepartmentService;
import com.oa.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SystemController {

    private final SysUserService userService;
    private final SysDepartmentService deptService;

    @GetMapping("/user")
    public Result<PageResult<SysUser>> userList(PageQuery query) {
        return Result.success(userService.pageQuery(query));
    }

    @PutMapping("/user/{id}")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        userService.updateById(user);
        return Result.success();
    }

    @GetMapping("/department")
    public Result<List<SysDepartment>> deptList() {
        return Result.success(deptService.list());
    }

    @PostMapping("/department")
    public Result<Void> addDept(@RequestBody SysDepartment dept) {
        deptService.save(dept);
        return Result.success();
    }

    @PutMapping("/department/{id}")
    public Result<Void> updateDept(@PathVariable Long id, @RequestBody SysDepartment dept) {
        dept.setId(id);
        deptService.updateById(dept);
        return Result.success();
    }

    @DeleteMapping("/department/{id}")
    public Result<Void> deleteDept(@PathVariable Long id) {
        deptService.removeById(id);
        return Result.success();
    }
}
```

- [ ] **Step 5: Build and commit**

```bash
cd OA/oa-server && mvn compile -q && cd ../..
git add oa-server/src/main/java/com/oa/service/ oa-server/src/main/java/com/oa/controller/
git commit -m "feat: add work log, meeting, and system management modules"
```

---

## Phase 8: MyBatis-Plus Config & MetaObjectHandler

### Task 15: Add MyBatis-Plus Configuration

**Files:**
- Create: `OA/oa-server/src/main/java/com/oa/config/MybatisPlusConfig.java`

- [ ] **Step 1: Create MybatisPlusConfig.java**

```java
package com.oa.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            }
        };
    }
}
```

- [ ] **Step 2: Commit**

```bash
cd OA/oa-server && mvn compile -q && cd ../..
git add oa-server/src/main/java/com/oa/config/MybatisPlusConfig.java
git commit -m "feat: add MyBatis-Plus auto-fill config"
```

---

## Phase 9: Frontend Core Layout

### Task 16: Set Up Frontend Core (main.js, router, stores, api/request.js)

**Files:**
- Modify: `OA/oa-web/src/main.js`
- Create: `OA/oa-web/src/router/index.js`
- Create: `OA/oa-web/src/stores/user.js`
- Create: `OA/oa-web/src/stores/app.js`
- Create: `OA/oa-web/src/api/request.js`
- Create: `OA/oa-web/src/utils/auth.js`

- [ ] **Step 1: Write main.js — register Element Plus, Router, Pinia**

```javascript
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(ElementPlus)
app.use(createPinia())
app.use(router)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.mount('#app')
```

- [ ] **Step 2: Write router/index.js**

```javascript
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('@/views/login/Login.vue') },
  {
    path: '/',
    component: () => import('@/views/layout/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/Dashboard.vue') },
      { path: 'notice', name: 'Notice', component: () => import('@/views/notice/NoticeList.vue') },
      { path: 'company-event', name: 'CompanyEvent', component: () => import('@/views/notice/CompanyEventList.vue') },
      { path: 'project-progress', name: 'ProjectProgress', component: () => import('@/views/notice/ProjectProgressList.vue') },
      { path: 'weekly-report', name: 'WeeklyReport', component: () => import('@/views/notice/WeeklyReportList.vue') },
      { path: 'incoming-file', name: 'IncomingFile', component: () => import('@/views/file/IncomingFileList.vue') },
      { path: 'outgoing-file', name: 'OutgoingFile', component: () => import('@/views/file/OutgoingFileList.vue') },
      { path: 'file-archive', name: 'FileArchive', component: () => import('@/views/file/FileArchiveList.vue') },
      { path: 'task', name: 'Task', component: () => import('@/views/task/TaskList.vue') },
      { path: 'leave', name: 'Leave', component: () => import('@/views/approval/LeaveList.vue') },
      { path: 'travel', name: 'Travel', component: () => import('@/views/approval/TravelList.vue') },
      { path: 'attendance', name: 'Attendance', component: () => import('@/views/approval/AttendanceList.vue') },
      { path: 'work-log', name: 'WorkLog', component: () => import('@/views/log/WorkLogList.vue') },
      { path: 'meeting-room', name: 'MeetingRoom', component: () => import('@/views/meeting/MeetingRoomList.vue') },
      { path: 'meeting', name: 'Meeting', component: () => import('@/views/meeting/MeetingList.vue') },
      { path: 'system', name: 'System', component: () => import('@/views/system/UserList.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
```

- [ ] **Step 3: Write stores/user.js**

```javascript
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, register, getUserInfo } from '@/api/auth'
import { getToken, setToken, removeToken } from '@/utils/auth'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)
  const role = ref('')

  async function doLogin(username, password) {
    const res = await login(username, password)
    setToken(res.data.token)
    userInfo.value = res.data
    role.value = res.data.role
    return res
  }

  async function doRegister(username, password, realName) {
    const res = await register(username, password, realName)
    setToken(res.data.token)
    userInfo.value = res.data
    role.value = res.data.role
    return res
  }

  async function fetchUserInfo() {
    const res = await getUserInfo()
    userInfo.value = res.data
    role.value = res.data.role
  }

  function logout() {
    removeToken()
    userInfo.value = null
    role.value = ''
  }

  return { userInfo, role, doLogin, doRegister, fetchUserInfo, logout }
})
```

- [ ] **Step 4: Write api/request.js (Axios interceptor)**

```javascript
import axios from 'axios'
import { getToken } from '@/utils/auth'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  response => {
    const { data } = response
    if (data.code !== 200) {
      ElMessage.error(data.message || '请求失败')
      return Promise.reject(new Error(data.message))
    }
    return data
  },
  error => {
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request
```

- [ ] **Step 5: Write api/auth.js**

```javascript
import request from './request'

export function login(username, password) {
  return request.post('/auth/login', { username, password })
}

export function register(username, password, realName) {
  return request.post('/auth/register', { username, password, realName })
}

export function getUserInfo() {
  return request.get('/auth/userinfo')
}
```

- [ ] **Step 6: Write utils/auth.js**

```javascript
const TOKEN_KEY = 'oa_token'

export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

export function removeToken() {
  localStorage.removeItem(TOKEN_KEY)
}
```

- [ ] **Step 7: Commit**

```bash
git add oa-web/src/
git commit -m "feat: set up frontend core (router, stores, axios, auth)"
```

---

### Task 17: Create Layout and Login Page

**Files:**
- Modify: `OA/oa-web/src/App.vue`
- Create: `OA/oa-web/src/views/login/Login.vue`
- Create: `OA/oa-web/src/views/layout/Layout.vue`
- Create: `OA/oa-web/src/components/common/Sidebar.vue`
- Create: `OA/oa-web/src/components/common/Navbar.vue`

- [ ] **Step 1: Write App.vue**

```vue
<template>
  <router-view />
</template>
```

- [ ] **Step 2: Write Login.vue**

```vue
<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>OA办公自动化系统</h2>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" style="width:100%">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="showRegister = true" style="width:100%">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog v-model="showRegister" title="用户注册" width="400px">
      <el-form :model="regForm">
        <el-form-item label="用户名"><el-input v-model="regForm.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="regForm.password" type="password" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="regForm.realName" /></el-form-item>
        <el-form-item><el-button type="primary" @click="handleRegister">注册</el-button></el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const loading = ref(false)
const showRegister = ref(false)
const regForm = reactive({ username: '', password: '', realName: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名' }],
  password: [{ required: true, message: '请输入密码' }]
}

async function handleLogin() {
  loading.value = true
  try {
    await userStore.doLogin(form.username, form.password)
    router.push('/')
    ElMessage.success('登录成功')
  } finally {
    loading.value = false
  }
}

async function handleRegister() {
  await userStore.doRegister(regForm.username, regForm.password, regForm.realName)
  showRegister.value = false
  router.push('/')
  ElMessage.success('注册成功')
}
</script>

<style scoped>
.login-container {
  display: flex; justify-content: center; align-items: center;
  height: 100vh; background: #f5f5f5;
}
.login-card { width: 400px; }
.login-card h2 { text-align: center; margin-bottom: 20px; }
</style>
```

- [ ] **Step 3: Write Layout.vue with el-container sidebar + main area**

```vue
<template>
  <el-container style="height:100vh">
    <el-aside width="220px" style="background:#304156">
      <Sidebar />
    </el-aside>
    <el-container>
      <el-header style="background:#fff;border-bottom:1px solid #e6e6e6">
        <Navbar />
      </el-header>
      <el-main style="background:#f0f2f5">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Sidebar from '@/components/common/Sidebar.vue'
import Navbar from '@/components/common/Navbar.vue'
</script>
```

- [ ] **Step 4: Write Sidebar.vue with menu items matching routes**

```vue
<template>
  <el-menu :default-active="route.path" router background-color="#304156"
           text-color="#bfcbd9" active-text-color="#409EFF">
    <el-menu-item index="/dashboard"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
    <el-sub-menu index="notice">
      <template #title><el-icon><Bell /></el-icon>信息发布</template>
      <el-menu-item index="/notice">公告栏</el-menu-item>
      <el-menu-item index="/company-event">公司活动</el-menu-item>
      <el-menu-item index="/project-progress">项目进度</el-menu-item>
      <el-menu-item index="/weekly-report">公司周报</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="file">
      <template #title><el-icon><Folder /></el-icon>文件管理</template>
      <el-menu-item index="/incoming-file">收文管理</el-menu-item>
      <el-menu-item index="/outgoing-file">发文管理</el-menu-item>
      <el-menu-item index="/file-archive">档案管理</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/task"><el-icon><List /></el-icon>工作任务</el-menu-item>
    <el-sub-menu index="approval">
      <template #title><el-icon><DocumentChecked /></el-icon>审批申请</template>
      <el-menu-item index="/leave">请假申请</el-menu-item>
      <el-menu-item index="/travel">出差申请</el-menu-item>
      <el-menu-item index="/attendance">出勤记录</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/work-log"><el-icon><Edit /></el-icon>工作日志</el-menu-item>
    <el-sub-menu index="meeting">
      <template #title><el-icon><Calendar /></el-icon>会议管理</template>
      <el-menu-item index="/meeting-room">会议室管理</el-menu-item>
      <el-menu-item index="/meeting">会议管理</el-menu-item>
    </el-sub-menu>
    <el-menu-item v-if="userStore.role==='admin'" index="/system">
      <el-icon><Setting /></el-icon>系统管理
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
const route = useRoute()
const userStore = useUserStore()
</script>
```

- [ ] **Step 5: Write Navbar.vue**

```vue
<template>
  <div style="display:flex;justify-content:space-between;align-items:center;height:60px;padding:0 20px">
    <span>OA办公系统</span>
    <el-dropdown>
      <span>{{ userStore.userInfo?.realName }} ({{ roleName }})</span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工' }
  return map[userStore.role] || ''
})

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>
```

- [ ] **Step 6: Commit**

```bash
git add oa-web/src/App.vue oa-web/src/views/login/ oa-web/src/views/layout/ oa-web/src/components/
git commit -m "feat: add login page and main layout with sidebar"
```

---

## Phase 10: Frontend Business Pages

Due to the number of pages (16+), each follows a CRUD table pattern with Element Plus `el-table` + `el-pagination` + `el-dialog` for create/edit.

### Task 18: Info Publishing Pages (4 pages)

**Files:**
- Create: `OA/oa-web/src/views/notice/NoticeList.vue`
- Create: `OA/oa-web/src/views/notice/CompanyEventList.vue`
- Create: `OA/oa-web/src/views/notice/ProjectProgressList.vue`
- Create: `OA/oa-web/src/views/notice/WeeklyReportList.vue`
- Create: `OA/oa-web/src/api/notice.js`

- [ ] **Step 1: Create api/notice.js**

```javascript
import request from './request'

export function getNotices(params) { return request.get('/notice', { params }) }
export function getNotice(id) { return request.get(`/notice/${id}`) }
export function createNotice(data) { return request.post('/notice', data) }
export function updateNotice(id, data) { return request.put(`/notice/${id}`, data) }
export function deleteNotice(id) { return request.delete(`/notice/${id}`) }

export function getCompanyEvents(params) { return request.get('/company-event', { params }) }
export function createCompanyEvent(data) { return request.post('/company-event', data) }
export function updateCompanyEvent(id, data) { return request.put(`/company-event/${id}`, data) }
export function deleteCompanyEvent(id) { return request.delete(`/company-event/${id}`) }

export function getProjectProgresses(params) { return request.get('/project-progress', { params }) }
export function createProjectProgress(data) { return request.post('/project-progress', data) }
export function updateProjectProgress(id, data) { return request.put(`/project-progress/${id}`, data) }
export function deleteProjectProgress(id) { return request.delete(`/project-progress/${id}`) }

export function getWeeklyReports(params) { return request.get('/weekly-report', { params }) }
export function createWeeklyReport(data) { return request.post('/weekly-report', data) }
export function updateWeeklyReport(id, data) { return request.put(`/weekly-report/${id}`, data) }
export function deleteWeeklyReport(id) { return request.delete(`/weekly-report/${id}`) }
```

- [ ] **Step 2: Create NoticeList.vue**

```vue
<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <el-input v-model="keyword" placeholder="搜索标题" style="width:240px" clearable @change="fetchData" />
        <el-button v-if="userStore.role==='admin'" type="primary" @click="openDialog()">发布公告</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='admin'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="pageSize"
                     @current-change="fetchData" layout="total, prev, pager, next" style="margin-top:16px" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑公告':'发布公告'" width="600px">
      <el-form :model="form">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="置顶"><el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="公告详情" width="600px">
      <h2>{{ detail.title }}</h2>
      <el-divider />
      <div v-html="detail.content" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getNotices, createNotice, updateNotice, deleteNotice } from '@/api/notice'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const records = ref([])
const total = ref(0)
const page = ref(1)
const pageSize = ref(10)
const keyword = ref('')

const dialogVisible = ref(false)
const detailVisible = ref(false)
const isEdit = ref(false)
const detail = ref({})
const form = reactive({ title: '', content: '', isTop: 0 })
let editId = null

async function fetchData() {
  const res = await getNotices({ page: page.value, pageSize: pageSize.value, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.title = row.title; form.content = row.content; form.isTop = row.isTop
  } else {
    isEdit.value = false; editId = null
    form.title = ''; form.content = ''; form.isTop = 0
  }
  dialogVisible.value = true
}

async function handleSave() {
  if (isEdit.value) {
    await updateNotice(editId, form)
  } else {
    await createNotice(form)
  }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteNotice(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
```

- [ ] **Step 3: Create CompanyEventList.vue** (same pattern with eventDate, location fields)

- [ ] **Step 4: Create ProjectProgressList.vue** (with progressPercent slider in form)

- [ ] **Step 5: Create WeeklyReportList.vue** (with reportWeek field)
- [ ] **Step 6: Commit**

```bash
git add oa-web/src/api/notice.js oa-web/src/views/notice/
git commit -m "feat: add info publishing frontend pages"
```

---

### Task 19: File Management Pages (3 pages)

**Files:**
- Create: `OA/oa-web/src/api/file.js`
- Create: `OA/oa-web/src/views/file/IncomingFileList.vue`
- Create: `OA/oa-web/src/views/file/OutgoingFileList.vue`
- Create: `OA/oa-web/src/views/file/FileArchiveList.vue`

Follow the same CRUD table pattern. Each page has: search bar, table with columns, dialog for create/edit.

The file.js API module:
```javascript
import request from './request'

export function getIncomingFiles(params) { return request.get('/incoming-file', { params }) }
export function createIncomingFile(data) { return request.post('/incoming-file', data) }
export function updateIncomingFile(id, data) { return request.put(`/incoming-file/${id}`, data) }
export function deleteIncomingFile(id) { return request.delete(`/incoming-file/${id}`) }

export function getOutgoingFiles(params) { return request.get('/outgoing-file', { params }) }
export function createOutgoingFile(data) { return request.post('/outgoing-file', data) }
export function updateOutgoingFile(id, data) { return request.put(`/outgoing-file/${id}`, data) }
export function deleteOutgoingFile(id) { return request.delete(`/outgoing-file/${id}`) }

export function getFileArchives(params) { return request.get('/file-archive', { params }) }
export function createFileArchive(data) { return request.post('/file-archive', data) }
export function updateFileArchive(id, data) { return request.put(`/file-archive/${id}`, data) }
export function deleteFileArchive(id) { return request.delete(`/file-archive/${id}`) }
```

Commit after implementation.

---

### Task 20: Task, Approval, Log, Meeting Pages (8 pages)

**Files:**
- Create: `OA/oa-web/src/api/task.js`, `OA/oa-web/src/api/approval.js`, `OA/oa-web/src/api/log.js`, `OA/oa-web/src/api/meeting.js`, `OA/oa-web/src/api/system.js`
- Create: `OA/oa-web/src/views/task/TaskList.vue`
- Create: `OA/oa-web/src/views/approval/LeaveList.vue` (with approve/reject buttons for manager/admin)
- Create: `OA/oa-web/src/views/approval/TravelList.vue`
- Create: `OA/oa-web/src/views/approval/AttendanceList.vue`
- Create: `OA/oa-web/src/views/log/WorkLogList.vue`
- Create: `OA/oa-web/src/views/meeting/MeetingRoomList.vue`
- Create: `OA/oa-web/src/views/meeting/MeetingList.vue`
- Create: `OA/oa-web/src/views/system/UserList.vue` + `OA/oa-web/src/views/system/DepartmentList.vue`

Layout pattern identical to Task 18: el-table + el-pagination + el-dialog.

Key differences:
- **TaskList.vue**: Two tabs — my tasks / assigned tasks. "分配任务" button for managers.
- **LeaveList.vue**: Approval buttons (同意/拒绝) that call PUT `/api/leave/{id}/approve`
- **MeetingList.vue**: Time conflict check before booking
- **System/UserList.vue**: Table with role dropdown to change user roles

### Task 21: Dashboard Page

**Files:**
- Create: `OA/oa-web/src/views/dashboard/Dashboard.vue`

- [ ] **Step 1: Create a simple dashboard**

```vue
<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6"><el-card><div class="stat">公告 <h2>12</h2></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat">任务 <h2>{{ taskCount }}</h2></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat">待审批 <h2>3</h2></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat">会议 <h2>5</h2></div></el-card></el-col>
    </el-row>
    <el-card style="margin-top:20px">
      <h3>欢迎使用OA办公自动化系统</h3>
      <p>当前角色：{{ roleName }}</p>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'
const userStore = useUserStore()
const taskCount = ref(5)
const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工' }
  return map[userStore.role] || ''
})
</script>

<style scoped>
.stat { text-align:center; }
.stat h2 { font-size:32px; margin:10px 0; }
</style>
```

- [ ] **Step 2: Commit all remaining frontend work**

```bash
git add oa-web/src/
git commit -m "feat: add all business pages and dashboard"
```

---

## Phase 11: Final Integration & Verification

### Task 22: Build and Verify

- [ ] **Step 1: Build backend**

Run: `cd OA/oa-server && mvn clean package -q`
Expected: BUILD SUCCESS, jar generated in target/

- [ ] **Step 2: Build frontend**

Run: `cd OA/oa-web && npm run build`
Expected: dist/ directory generated

- [ ] **Step 3: Final commit**

```bash
git add -A && git commit -m "chore: final build verification"
```

---

## Implementation Order Summary

| Phase | Tasks | What |
|---|---|---|
| 1 | 1-2 | Project scaffolding (backend + frontend) |
| 2 | 3-4 | Common infrastructure (Result, JWT, Security) |
| 3 | 5-7 | Database schema + all entities + all mappers |
| 4 | 8-9 | Auth DTOs + AuthController + UserService |
| 5 | 10 | Info publishing CRUD (notice/event/progress/weekly) |
| 6 | 11 | File management CRUD (incoming/outgoing/archive) |
| 7 | 12-14 | Task, Approval, Log, Meeting, System modules |
| 8 | 15 | MyBatis-Plus config |
| 9 | 16-17 | Frontend core (router, stores, auth, layout) |
| 10 | 18-21 | All frontend business pages + dashboard |
| 11 | 22 | Final build verification |
