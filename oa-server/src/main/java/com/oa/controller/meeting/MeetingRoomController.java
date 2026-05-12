package com.oa.controller.meeting;

import com.oa.common.Result;
import com.oa.entity.OaMeetingRoom;
import com.oa.service.OaMeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-room")
@RequiredArgsConstructor
public class MeetingRoomController {

    private final OaMeetingRoomService roomService;

    @GetMapping
    public Result<List<OaMeetingRoom>> list() {
        return Result.success(roomService.list());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('管理员')")
    public Result<Void> create(@RequestBody OaMeetingRoom room) {
        roomService.save(room);
        return Result.success();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('管理员')")
    public Result<Void> update(@PathVariable Long id, @RequestBody OaMeetingRoom room) {
        room.setId(id);
        roomService.updateById(room);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('管理员')")
    public Result<Void> delete(@PathVariable Long id) {
        roomService.removeById(id);
        return Result.success();
    }
}
