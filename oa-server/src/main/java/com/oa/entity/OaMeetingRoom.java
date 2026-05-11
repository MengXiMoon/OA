package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("oa_meeting_room")
public class OaMeetingRoom {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer hasProjector;
    private String status;
}
