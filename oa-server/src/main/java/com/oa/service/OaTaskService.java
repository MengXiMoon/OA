package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.OaTask;
import com.oa.mapper.OaTaskMapper;
import org.springframework.stereotype.Service;

@Service
public class OaTaskService extends ServiceImpl<OaTaskMapper, OaTask> {

    public PageResult<OaTask> getMyTasks(PageQuery query) {
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaTask> wrapper = new LambdaQueryWrapper<OaTask>()
                .eq(OaTask::getAssigneeId, userId)
                .orderByDesc(OaTask::getCreateTime);
        Page<OaTask> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public PageResult<OaTask> getAssignedTasks(PageQuery query) {
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<OaTask> wrapper = new LambdaQueryWrapper<OaTask>()
                .eq(OaTask::getCreatorId, userId)
                .orderByDesc(OaTask::getCreateTime);
        Page<OaTask> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public void assignTask(OaTask task) {
        task.setCreatorId(SecurityUtils.getCurrentUserId());
        task.setStatus("pending");
        save(task);
    }
}
