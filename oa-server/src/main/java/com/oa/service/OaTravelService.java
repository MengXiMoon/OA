package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaTravel;
import com.oa.mapper.OaTravelMapper;
import org.springframework.stereotype.Service;

@Service
public class OaTravelService extends ServiceImpl<OaTravelMapper, OaTravel> {

    public PageResult<OaTravel> pageQuery(PageQuery query) {
        String role = SecurityUtils.getCurrentRole();
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaTravel> wrapper = new LambdaQueryWrapper<>();
        if ("employee".equals(role)) {
            wrapper.eq(OaTravel::getApplicantId, userId);
        } else if ("manager".equals(role)) {
            wrapper.eq(OaTravel::getApproverId, userId).or().eq(OaTravel::getApplicantId, userId);
        }
        wrapper.orderByDesc(OaTravel::getCreateTime);
        Page<OaTravel> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void approve(Long id, String status) {
        OaTravel travel = getById(id);
        travel.setStatus(status);
        travel.setApproverId(SecurityUtils.getCurrentUserId());
        updateById(travel);
    }
}
