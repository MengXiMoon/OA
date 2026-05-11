package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_task")
public class OaTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long creatorId;
    private Long assigneeId;
    private LocalDateTime deadline;
    private String priority;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
