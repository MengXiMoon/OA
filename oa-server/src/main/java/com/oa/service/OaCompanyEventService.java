package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaCompanyEvent;
import com.oa.mapper.OaCompanyEventMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaCompanyEventService extends ServiceImpl<OaCompanyEventMapper, OaCompanyEvent> {

    public PageResult<OaCompanyEvent> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaCompanyEvent> wrapper = new LambdaQueryWrapper<OaCompanyEvent>()
                .orderByDesc(OaCompanyEvent::getCreateTime);
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(OaCompanyEvent::getTitle, query.getKeyword());
        }
        Page<OaCompanyEvent> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void publish(OaCompanyEvent event) {
        event.setPublisherId(SecurityUtils.getCurrentUserId());
        save(event);
    }
}
