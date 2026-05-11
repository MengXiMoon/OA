package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.dto.PageQuery;
import com.oa.entity.OaProjectProgress;
import com.oa.mapper.OaProjectProgressMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaProjectProgressService extends ServiceImpl<OaProjectProgressMapper, OaProjectProgress> {

    public PageResult<OaProjectProgress> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaProjectProgress> wrapper = new LambdaQueryWrapper<OaProjectProgress>()
                .orderByDesc(OaProjectProgress::getCreateTime);
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(OaProjectProgress::getProjectName, query.getKeyword());
        }
        Page<OaProjectProgress> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
