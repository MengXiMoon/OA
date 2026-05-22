package com.oa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Value("${ai.endpoint:https://api.deepseek.com/v1/chat/completions}")
    private String endpoint;

    @Value("${ai.api-key:}")
    private String apiKey;

    @Value("${ai.model:deepseek-chat}")
    private String model;

    @Value("${ai.timeout:30}")
    private int timeout;

    public String getEndpoint() { return endpoint; }
    public String getApiKey() { return apiKey; }
    public String getModel() { return model; }
    public int getTimeout() { return timeout; }
}
