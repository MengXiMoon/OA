package com.oa.controller.notice;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaProjectProgress;
import com.oa.service.OaProjectProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-progress")
@RequiredArgsConstructor
public class ProjectProgressController {

    private final OaProjectProgressService service;

    @GetMapping
    public Result<PageResult<OaProjectProgress>> list(PageQuery query) {
        return Result.success(service.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaProjectProgress> detail(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> create(@RequestBody OaProjectProgress progress) {
        service.save(progress);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaProjectProgress progress) {
        progress.setId(id);
        service.updateById(progress);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success();
    }
}
