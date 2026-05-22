package com.oa.service;

import com.oa.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class NotificationService {

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    public void notifyApprovalResult(String applicantName, String type, String status) {
        log.info("审批通知：{} 的{}申请 → {}", applicantName, type, status);
        if (rabbitTemplate == null) return;
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, "approval.result",
                Map.of("type", "approval", "applicantName", applicantName, "approvalType", type, "status", status));
    }

    public void notifyTaskAssigned(String assigneeName, String taskTitle) {
        log.info("任务分配通知：{} ← {}", assigneeName, taskTitle);
        if (rabbitTemplate == null) return;
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, "task.assign",
                Map.of("type", "task_assign", "assigneeName", assigneeName, "taskTitle", taskTitle));
    }

    public void notifyNoticePublished(String title) {
        log.info("公告发布通知：{}", title);
        if (rabbitTemplate == null) return;
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, "notice.publish",
                Map.of("type", "notice_publish", "title", title));
    }
}
