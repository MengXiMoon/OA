package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_incoming_file")
public class OaIncomingFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileNo;
    private String title;
    private String sendOrg;
    private LocalDate receiveDate;
    private String fileType;
    private String content;
    private String attachmentUrl;
    private Long handlerId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
