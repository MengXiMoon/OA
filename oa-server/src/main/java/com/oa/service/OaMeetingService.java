package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaMeeting;
import com.oa.mapper.OaMeetingMapper;
import org.springframework.stereotype.Service;

@Service
public class OaMeetingService extends ServiceImpl<OaMeetingMapper, OaMeeting> {

    public PageResult<OaMeeting> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaMeeting> wrapper = new LambdaQueryWrapper<OaMeeting>()
                .orderByDesc(OaMeeting::getStartTime);
        Page<OaMeeting> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void schedule(OaMeeting meeting) {
        meeting.setOrganizerId(SecurityUtils.getCurrentUserId());
        meeting.setStatus("scheduled");
        save(meeting);
    }
}
