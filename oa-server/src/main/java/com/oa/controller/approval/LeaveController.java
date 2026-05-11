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
