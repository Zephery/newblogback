package com.myblog.service;

import com.myblog.model.Admin;

/**
 * Created by Zephery on 2017/7/1.
 */
public interface IAdminService {
    public Admin login(String username, String password);
    public Admin getByAdminname(String adminname);
}
