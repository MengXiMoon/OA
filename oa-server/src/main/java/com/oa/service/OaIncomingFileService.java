package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.BusinessException;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaIncomingFile;
import com.oa.mapper.OaIncomingFileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OaIncomingFileService extends ServiceImpl<OaIncomingFileMapper, OaIncomingFile> {

    public PageResult<OaIncomingFile> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        LambdaQueryWrapper<OaIncomingFile> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            throw new BusinessException(403, "无权访问");
        }
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w.like(OaIncomingFile::getTitle, query.getKeyword())
                    .or().like(OaIncomingFile::getFileNo, query.getKeyword()));
        }
        wrapper.orderByDesc(OaIncomingFile::getCreateTime);
        Page<OaIncomingFile> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
