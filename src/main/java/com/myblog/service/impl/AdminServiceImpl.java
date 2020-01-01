package com.myblog.service.impl;

import com.myblog.dao.AdminMapper;
import com.myblog.model.Admin;
import com.myblog.service.IAdminService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Zephery on 2017/7/1.
 */
@Service("adminService")
@Component

public class AdminServiceImpl implements IAdminService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AdminMapper adminMapper;

    public Admin login(String username, String password) {
        return new Admin();
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#adminname")
    public Admin getByAdminname(String adminname) {
        return adminMapper.getByAdminname(adminname);
    }
}
