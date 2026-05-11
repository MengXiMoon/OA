package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_work_log")
public class OaWorkLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDate logDate;
    private String todayContent;
    private String tomorrowPlan;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
