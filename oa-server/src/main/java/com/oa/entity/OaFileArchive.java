package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("oa_file_archive")
public class OaFileArchive {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileNo;
    private String title;
    private String fileType;
    private String keywords;
    private LocalDate archiveDate;
    private String fileUrl;
    private Long deptId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
