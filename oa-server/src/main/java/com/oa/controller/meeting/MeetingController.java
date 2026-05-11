package com.oa.controller.meeting;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaMeeting;
import com.oa.service.OaMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final OaMeetingService meetingService;

    @GetMapping
    public Result<PageResult<OaMeeting>> list(PageQuery query) {
        return Result.success(meetingService.pageQuery(query));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> create(@RequestBody OaMeeting meeting) {
        meetingService.schedule(meeting);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaMeeting meeting) {
        meeting.setId(id);
        meetingService.updateById(meeting);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> delete(@PathVariable Long id) {
        meetingService.removeById(id);
        return Result.success();
    }
}
