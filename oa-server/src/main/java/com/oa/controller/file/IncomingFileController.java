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
    @PreAuthorize("hasAnyAuthority('管理员', '部门经理')")
    public Result<Void> create(@RequestBody OaIncomingFile file) {
        service.save(file);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('管理员', '部门经理')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaIncomingFile file) {
        file.setId(id);
        service.updateById(file);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('管理员', '部门经理')")
    public Result<Void> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success();
    }
}
