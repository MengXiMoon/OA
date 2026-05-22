package com.oa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AIConfig {
    /** LLM API 地址（如 DeepSeek / 通义千问 / OpenAI 兼容接口） */
    private String endpoint = "https://api.deepseek.com/v1/chat/completions";
    /** API Key */
    private String apiKey = "";
    /** 模型名称 */
    private String model = "deepseek-chat";
    /** 超时秒数 */
    private int timeout = 30;
}
