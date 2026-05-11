package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.BusinessException;
import com.oa.common.PageResult;
import com.oa.common.SecurityUtils;
import com.oa.dto.PageQuery;
import com.oa.entity.SysUser;
import com.oa.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private final PasswordEncoder passwordEncoder;

    public SysUser register(String username, String password, String realName) {
        if (lambdaQuery().eq(SysUser::getUsername, username).count() > 0) {
            throw new BusinessException("用户名已存在");
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRealName(realName);
        user.setRole("employee");
        user.setStatus(1);
        save(user);
        return user;
    }

    public PageResult<SysUser> pageQuery(PageQuery query) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(SysUser::getUsername, query.getKeyword())
                   .or().like(SysUser::getRealName, query.getKeyword());
        }
        wrapper.orderByDesc(SysUser::getCreateTime);
        Page<SysUser> page = page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    public List<SysUser> getUsersByDept(Long deptId) {
        return lambdaQuery().eq(SysUser::getDeptId, deptId).list();
    }

    public List<SysUser> getSubordinates() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        SysUser current = getById(currentUserId);
        return lambdaQuery().eq(SysUser::getDeptId, current.getDeptId()).list();
    }
}
