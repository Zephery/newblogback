package com.myblog.service.impl;

import com.myblog.dao.AdminMapper;
import com.myblog.model.Admin;
import com.myblog.service.IAdminService;
import org.springframework.context.annotation.Bean;
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
    private AdminMapper adminMapper;

    public Admin login(String username, String password){
        return new Admin();
    }
    @Override
    public Admin getByAdminname(String adminname){
        return adminMapper.getByAdminname(adminname);
    }
}
