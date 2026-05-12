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
    @PreAuthorize("hasRole('管理员')")
    public Result<PageResult<OaAttendance>> listAll(PageQuery query) {
        return Result.success(attendanceService.pageQueryAll(query));
    }
}
