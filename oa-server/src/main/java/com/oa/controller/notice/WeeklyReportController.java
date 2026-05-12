package com.oa.controller.notice;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaWeeklyReport;
import com.oa.service.OaWeeklyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weekly-report")
@RequiredArgsConstructor
public class WeeklyReportController {

    private final OaWeeklyReportService service;

    @GetMapping
    public Result<PageResult<OaWeeklyReport>> list(PageQuery query) {
        return Result.success(service.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaWeeklyReport> detail(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> create(@RequestBody OaWeeklyReport report) {
        service.publish(report);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaWeeklyReport report) {
        report.setId(id);
        service.updateById(report);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success();
    }
}
