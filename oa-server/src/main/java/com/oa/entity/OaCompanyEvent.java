package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_company_event")
public class OaCompanyEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String coverImage;
    private LocalDate eventDate;
    private String location;
    private Long publisherId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
