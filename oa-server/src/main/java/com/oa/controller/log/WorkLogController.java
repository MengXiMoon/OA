package com.oa.controller.log;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaWorkLog;
import com.oa.service.OaWorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-log")
@RequiredArgsConstructor
public class WorkLogController {

    private final OaWorkLogService workLogService;

    @GetMapping
    public Result<PageResult<OaWorkLog>> list(PageQuery query) {
        return Result.success(workLogService.pageQuery(query));
    }

    @PostMapping
    public Result<Void> create(@RequestBody OaWorkLog log) {
        log.setUserId(SecurityUtils.getCurrentUserId());
        workLogService.save(log);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaWorkLog log) {
        log.setId(id);
        workLogService.updateById(log);
        return Result.success();
    }
}
