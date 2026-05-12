package com.oa.controller.notice;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaNotice;
import com.oa.service.OaNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final OaNoticeService noticeService;

    @GetMapping
    public Result<PageResult<OaNotice>> list(PageQuery query) {
        return Result.success(noticeService.pageQuery(query));
    }

    @GetMapping("/{id}")
    public Result<OaNotice> detail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> create(@RequestBody OaNotice notice) {
        noticeService.publish(notice);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaNotice notice) {
        notice.setId(id);
        noticeService.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('管理员')")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.success();
    }
}
