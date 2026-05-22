package com.oa.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 通知交换机
    public static final String NOTIFICATION_EXCHANGE = "oa.notification.exchange";

    // 审批通知队列（请假/出差审批结果）
    public static final String APPROVAL_QUEUE = "oa.approval.notify";
    // 任务分配通知队列
    public static final String TASK_ASSIGN_QUEUE = "oa.task.assign.notify";
    // 公告发布通知队列
    public static final String NOTICE_PUBLISH_QUEUE = "oa.notice.publish.notify";

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue approvalQueue() {
        return QueueBuilder.durable(APPROVAL_QUEUE).build();
    }

    @Bean
    public Queue taskAssignQueue() {
        return QueueBuilder.durable(TASK_ASSIGN_QUEUE).build();
    }

    @Bean
    public Queue noticePublishQueue() {
        return QueueBuilder.durable(NOTICE_PUBLISH_QUEUE).build();
    }

    @Bean
    public Binding approvalBinding() {
        return BindingBuilder.bind(approvalQueue())
                .to(notificationExchange()).with("approval.#");
    }

    @Bean
    public Binding taskAssignBinding() {
        return BindingBuilder.bind(taskAssignQueue())
                .to(notificationExchange()).with("task.assign");
    }

    @Bean
    public Binding noticePublishBinding() {
        return BindingBuilder.bind(noticePublishQueue())
                .to(notificationExchange()).with("notice.publish");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
