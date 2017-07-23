package com.myblog.controller;

import com.myblog.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zephery on 2017/6/27.
 */
@RestController
public class LogController {
    @Autowired
    private ILogService logService;


    @GetMapping("updatelog")
    public String updatelog() {
        return "success";
    }

}
