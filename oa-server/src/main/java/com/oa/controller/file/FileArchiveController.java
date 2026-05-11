package com.oa.controller.file;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaFileArchive;
import com.oa.service.OaFileArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/file-archive")
@RequiredArgsConstructor
public class FileArchiveController {

    private final OaFileArchiveService service;

    @GetMapping
    public Result<PageResult<OaFileArchive>> list(PageQuery query) {
        return Result.success(service.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaFileArchive> detail(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> create(@RequestBody OaFileArchive file) {
        service.save(file);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaFileArchive file) {
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
