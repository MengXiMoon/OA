package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_department")
public class SysDepartment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private Long leaderId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
