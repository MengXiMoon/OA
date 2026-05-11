package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.BusinessException;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaOutgoingFile;
import com.oa.mapper.OaOutgoingFileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaOutgoingFileService extends ServiceImpl<OaOutgoingFileMapper, OaOutgoingFile> {

    public PageResult<OaOutgoingFile> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        LambdaQueryWrapper<OaOutgoingFile> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            throw new BusinessException(403, "无权访问");
        }
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w.like(OaOutgoingFile::getTitle, query.getKeyword())
                    .or().like(OaOutgoingFile::getFileNo, query.getKeyword()));
        }
        wrapper.orderByDesc(OaOutgoingFile::getCreateTime);
        Page<OaOutgoingFile> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
