package com.myblog.controller;

import com.myblog.service.ILogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Zephery on 2017/6/27.
 */
@RestController
public class LogController {
    @Resource
    private ILogService logService;


    @GetMapping("updatelog")
    public String updatelog() {
        return "success";
    }

}
