package com.oa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息消费者 — 异步处理通知（实际项目中可对接邮件/短信/企业微信）
 */
@Slf4j
@Component
public class MessageConsumer {

    /**
     * 消费审批通知
     * 实际项目可：发送邮件、推送企业微信消息、短信通知等
     */
    @RabbitListener(queues = "oa.approval.notify")
    public void handleApprovalNotification(Map<String, Object> message) {
        log.info("📧 [模拟发送邮件] 审批结果通知：{} 的{}申请结果 → {}",
                message.get("applicantName"),
                message.get("approvalType"),
                message.get("status"));
    }

    /**
     * 消费任务分配通知
     */
    @RabbitListener(queues = "oa.task.assign.notify")
    public void handleTaskAssignNotification(Map<String, Object> message) {
        log.info("📧 [模拟发送邮件] 新任务分配：{} 收到任务「{}」",
                message.get("assigneeName"),
                message.get("taskTitle"));
    }

    /**
     * 消费公告发布通知
     */
    @RabbitListener(queues = "oa.notice.publish.notify")
    public void handleNoticePublishNotification(Map<String, Object> message) {
        log.info("📧 [模拟发送邮件] 新公告发布：{}", message.get("title"));
    }
}
