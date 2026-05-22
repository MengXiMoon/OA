package com.oa.controller;

import com.oa.common.Result;
import com.oa.config.AIConfig;
import com.oa.service.AIService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;
    private final AIConfig aiConfig;

    public AIController(AIService aiService, AIConfig aiConfig) {
        this.aiService = aiService;
        this.aiConfig = aiConfig;
    }

    /** AI 配置信息 */
    @GetMapping("/config")
    public Result<Map<String, Object>> config() {
        return Result.success(Map.of(
                "model", aiConfig.getModel(),
                "endpoint", aiConfig.getEndpoint().contains("dashscope") ? "通义千问" : "DeepSeek"
        ));
    }

    /** AI 生成周报摘要 */
    @PostMapping("/weekly-summary")
    public Result<String> weeklySummary(@RequestBody SummaryRequest request) {
        String summary = aiService.generateWeeklySummary(request.getLogs());
        return Result.success(summary);
    }

    /** AI 任务优先级建议 */
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
