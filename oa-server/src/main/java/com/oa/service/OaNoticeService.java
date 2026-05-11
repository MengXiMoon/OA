package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaNotice;
import com.oa.mapper.OaNoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaNoticeService extends ServiceImpl<OaNoticeMapper, OaNotice> {

    public PageResult<OaNotice> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaNotice> wrapper = new LambdaQueryWrapper<OaNotice>()
                .orderByDesc(OaNotice::getIsTop)
                .orderByDesc(OaNotice::getCreateTime);
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(OaNotice::getTitle, query.getKeyword());
        }
        Page<OaNotice> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void publish(OaNotice notice) {
        notice.setPublisherId(SecurityUtils.getCurrentUserId());
        save(notice);
    }
}
