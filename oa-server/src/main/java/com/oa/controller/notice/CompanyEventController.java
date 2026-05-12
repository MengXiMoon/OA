package com.oa.controller.notice;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaCompanyEvent;
import com.oa.service.OaCompanyEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-event")
@RequiredArgsConstructor
public class CompanyEventController {

    private final OaCompanyEventService service;

    @GetMapping
    public Result<PageResult<OaCompanyEvent>> list(PageQuery query) {
        return Result.success(service.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaCompanyEvent> detail(@PathVariable Long id) {
        return Result.success(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> create(@RequestBody OaCompanyEvent event) {
        service.publish(event);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaCompanyEvent event) {
        event.setId(id);
        service.updateById(event);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success();
    }
}
