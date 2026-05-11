package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oa_project_progress")
public class OaProjectProgress {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String projectName;
    private String content;
    private Integer progressPercent;
    private Long responsibleId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
