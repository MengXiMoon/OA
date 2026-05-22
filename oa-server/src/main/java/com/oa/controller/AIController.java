package com.oa.controller;

import com.oa.common.Result;
import com.oa.service.AIService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    /**
     * AI 生成周报摘要
     */
    @PostMapping("/weekly-summary")
    public Result<String> weeklySummary(@RequestBody SummaryRequest request) {
        String summary = aiService.generateWeeklySummary(request.getLogs());
        return Result.success(summary);
    }

    /**
     * AI 任务优先级建议
     */
    @PostMapping("/task-suggest")
    public Result<String> taskSuggest(@RequestBody TaskSuggestRequest request) {
        String suggestion = aiService.suggestTaskPriority(request.getTasks());
        return Result.success(suggestion);
    }

    @Data
    public static class SummaryRequest {
        private List<String> logs;
    }

    @Data
    public static class TaskSuggestRequest {
        private List<String> tasks;
    }
}
