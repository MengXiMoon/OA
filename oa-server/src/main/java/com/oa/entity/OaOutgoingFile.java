package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_outgoing_file")
public class OaOutgoingFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileNo;
    private String title;
    private String sendToOrg;
    private LocalDate sendDate;
    private String fileType;
    private String content;
    private String attachmentUrl;
    private Long drafterId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
