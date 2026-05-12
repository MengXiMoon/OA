package com.oa.controller.file;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaOutgoingFile;
import com.oa.service.OaOutgoingFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/outgoing-file")
@RequiredArgsConstructor
public class OutgoingFileController {

    private final OaOutgoingFileService service;

    @GetMapping
    public Result<PageResult<OaOutgoingFile>> list(PageQuery query) {
        return Result.success(service.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaOutgoingFile> detail(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('管理员', '部门经理')")
    public Result<Void> create(@RequestBody OaOutgoingFile file) {
        service.save(file);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('管理员', '部门经理')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaOutgoingFile file) {
        file.setId(id);
        service.updateById(file);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('管理员', '部门经理')")
    public Result<Void> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success();
    }
}
