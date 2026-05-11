package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_leave")
public class OaLeave {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long applicantId;
    private String leaveType;
    private String reason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long approverId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
