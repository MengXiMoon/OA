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
    @PreAuthorize("hasAnyRole('管理员', '部门经理')")
    public Result<PageResult<OaTask>> assignedTasks(PageQuery query) {
        return Result.success(taskService.getAssignedTasks(query));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('管理员', '部门经理')")
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
