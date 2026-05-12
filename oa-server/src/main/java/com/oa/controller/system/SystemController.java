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
@PreAuthorize("hasAuthority('管理员')")
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
