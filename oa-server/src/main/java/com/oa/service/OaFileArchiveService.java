package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.dto.PageQuery;
import com.oa.entity.OaFileArchive;
import com.oa.mapper.OaFileArchiveMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaFileArchiveService extends ServiceImpl<OaFileArchiveMapper, OaFileArchive> {

    public PageResult<OaFileArchive> pageQuery(PageQuery query) {
        LambdaQueryWrapper<OaFileArchive> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w.like(OaFileArchive::getTitle, query.getKeyword())
                    .or().like(OaFileArchive::getFileNo, query.getKeyword()));
        }
        wrapper.orderByDesc(OaFileArchive::getCreateTime);
        Page<OaFileArchive> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
