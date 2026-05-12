package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaLeave;
import com.oa.mapper.OaLeaveMapper;
import org.springframework.stereotype.Service;

@Service
public class OaLeaveService extends ServiceImpl<OaLeaveMapper, OaLeave> {

    public PageResult<OaLeave> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaLeave> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            wrapper.eq(OaLeave::getApplicantId, userId);
        } else if ("manager".equals(role)) {
            wrapper.eq(OaLeave::getApproverId, userId).or().eq(OaLeave::getApplicantId, userId);
        }
        wrapper.orderByDesc(OaLeave::getCreateTime);
        Page<OaLeave> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    @Override
    public boolean save(OaLeave leave) {
        leave.setApplicantId(SecurityUtils.getCurrentUserId());
        leave.setStatus("pending");
        return super.save(leave);
    }

    public void approve(Long id, String status) {
        OaLeave leave = getById(id);
        leave.setStatus(status);
        leave.setApproverId(SecurityUtils.getCurrentUserId());
        updateById(leave);
    }
}
