package com.oa.controller.approval;

import com.oa.common.PageResult;
import com.oa.common.Result;
import com.oa.dto.PageQuery;
import com.oa.entity.OaTravel;
import com.oa.service.OaTravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {

    private final OaTravelService travelService;

    @GetMapping
    public Result<PageResult<OaTravel>> list(PageQuery query) {
        return Result.success(travelService.pageQuery(query));
    }

    @PostMapping
    public Result<Void> apply(@RequestBody OaTravel travel) {
        travelService.save(travel);
        return Result.success();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Result<Void> approve(@PathVariable Long id, @RequestBody Map<String, String> body) {
        travelService.approve(id, body.get("status"));
        return Result.success();
    }
}
