package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaWeeklyReport;
import com.oa.mapper.OaWeeklyReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaWeeklyReportService extends ServiceImpl<OaWeeklyReportMapper, OaWeeklyReport> {

    public PageResult<OaWeeklyReport> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaWeeklyReport> wrapper = new LambdaQueryWrapper<OaWeeklyReport>()
                .orderByDesc(OaWeeklyReport::getCreateTime);
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(OaWeeklyReport::getTitle, query.getKeyword());
        }
        Page<OaWeeklyReport> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void publish(OaWeeklyReport report) {
        report.setReporterId(SecurityUtils.getCurrentUserId());
        save(report);
    }
}
