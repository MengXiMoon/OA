package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaAttendance;
import com.oa.mapper.OaAttendanceMapper;
import org.springframework.stereotype.Service;

@Service
public class OaAttendanceService extends ServiceImpl<OaAttendanceMapper, OaAttendance> {

    public PageResult<OaAttendance> pageQuery(PageQuery query) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<OaAttendance> page = page(new Page<>(query.getPage(), query.getPageSize()),
                new LambdaQueryWrapper<OaAttendance>().eq(OaAttendance::getUserId, userId));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public PageResult<OaAttendance> pageQueryAll(PageQuery query) {
        Page<OaAttendance> page = page(new Page<>(query.getPage(), query.getPageSize()));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
