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
