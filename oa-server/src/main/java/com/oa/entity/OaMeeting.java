package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_meeting")
public class OaMeeting {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long roomId;
    private Long organizerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
