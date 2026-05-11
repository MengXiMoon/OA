package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaWorkLog;
import com.oa.mapper.OaWorkLogMapper;
import org.springframework.stereotype.Service;

@Service
public class OaWorkLogService extends ServiceImpl<OaWorkLogMapper, OaWorkLog> {

    public PageResult<OaWorkLog> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaWorkLog> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            wrapper.eq(OaWorkLog::getUserId, userId);
        }
        wrapper.orderByDesc(OaWorkLog::getLogDate);
        Page<OaWorkLog> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
