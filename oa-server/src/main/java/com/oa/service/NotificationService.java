package com.oa.service;

import com.oa.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 异步通知服务 — 将通知消息发送到 RabbitMQ，由消费者异步处理
 * （实际邮件/短信发送在 MessageConsumer 中模拟）
 */
@Slf4j
@Service
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /** 审批结果通知 */
    public void notifyApprovalResult(String applicantName, String type, String status) {
        Map<String, Object> msg = Map.of(
                "type", "approval",
                "applicantName", applicantName,
                "approvalType", type,
                "status", status,
                "timestamp", System.currentTimeMillis()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, "approval.result", msg);
        log.info("已发送审批通知消息：{} 的{}申请 → {}", applicantName, type, status);
    }

    /** 任务分配通知 */
    public void notifyTaskAssigned(String assigneeName, String taskTitle) {
        Map<String, Object> msg = Map.of(
                "type", "task_assign",
                "assigneeName", assigneeName,
                "taskTitle", taskTitle,
                "timestamp", System.currentTimeMillis()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, "task.assign", msg);
        log.info("已发送任务分配通知：{} ← {}", assigneeName, taskTitle);
    }

    /** 公告发布通知 */
    public void notifyNoticePublished(String title) {
        Map<String, Object> msg = Map.of(
                "type", "notice_publish",
                "title", title,
                "timestamp", System.currentTimeMillis()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, "notice.publish", msg);
        log.info("已发送公告发布通知：{}", title);
    }
}
