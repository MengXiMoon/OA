package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_weekly_report")
public class OaWeeklyReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long deptId;
    private Long reporterId;
    private String reportWeek;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
