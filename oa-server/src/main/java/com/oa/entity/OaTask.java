package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private String priority;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
