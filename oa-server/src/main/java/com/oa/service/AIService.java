package com.oa.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oa.config.AIConfig;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final AIConfig aiConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AIService(AIConfig aiConfig, ObjectMapper objectMapper) {
        this.aiConfig = aiConfig;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = objectMapper;
    }

    /**
     * 调用 LLM 生成周报摘要
     * @param logs 本周工作日志列表
     * @return AI 生成的周报摘要
     */
    public String generateWeeklySummary(List<String> logs) {
        String prompt = buildWeeklySummaryPrompt(logs);
        return callLLM(prompt);
    }

    /**
     * 调用 LLM 分析任务并给出优先级建议
     * @param tasks 当前任务列表
     * @return AI 的优先级建议
     */
    public String suggestTaskPriority(List<String> tasks) {
        String prompt = buildTaskSuggestPrompt(tasks);
        return callLLM(prompt);
    }

    private String buildWeeklySummaryPrompt(List<String> logs) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是办公助手。请根据以下员工本周工作日志，生成一段200字以内的周报摘要，包含：本周主要工作、进展亮点、存在问题。\n\n");
        for (int i = 0; i < logs.size(); i++) {
            sb.append("日志").append(i + 1).append("：").append(logs.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String buildTaskSuggestPrompt(List<String> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是办公助手。请根据以下任务列表，分析每个任务的紧急程度和重要性，给出优先级排序建议（高/中/低），100字以内。\n\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("任务").append(i + 1).append("：").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String callLLM(String prompt) {
        if (aiConfig.getApiKey() == null || aiConfig.getApiKey().isBlank()) {
            return "AI 服务未配置 API Key，请在 application.yml 中设置 ai.api-key";
        }

        try {
            Map<String, Object> body = Map.of(
                    "model", aiConfig.getModel(),
                    "input", Map.of("messages", List.of(
                            Map.of("role", "system", "content", "你是一个专业的办公助手，回答简洁准确。"),
                            Map.of("role", "user", "content", prompt)
                    )),
                    "parameters", Map.of(
                            "max_tokens", 500,
                            "temperature", 0.7
                    )
            );

            String json = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(aiConfig.getEndpoint()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + aiConfig.getApiKey())
                    .timeout(Duration.ofSeconds(aiConfig.getTimeout()))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                DashScopeResponse dr = objectMapper.readValue(response.body(), DashScopeResponse.class);
                if (dr.getOutput() != null && dr.getOutput().getChoices() != null
                        && !dr.getOutput().getChoices().isEmpty()) {
                    return dr.getOutput().getChoices().get(0).getMessage().getContent();
                }
            }
            return "AI 调用失败，状态码：" + response.statusCode() + "，响应：" + response.body().substring(0, Math.min(200, response.body().length()));
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "AI 服务调用异常：" + e.getMessage();
        }
    }

    @Data
    public static class DashScopeResponse {
        private Output output;

        @Data
        public static class Output {
            private List<Choice> choices;
        }

        @Data
        public static class Choice {
            @JsonProperty("finish_reason")
            private String finishReason;
            private Message message;
        }

        @Data
        public static class Message {
            private String content;
            private String role;
        }
    }
}
