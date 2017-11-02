package com.myblog.controller;

import com.myblog.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zephery
 * @since 2017/11/2 19:02
 * Description:
 */
@RestController
public class CommonController {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Resource
    private ITagService tagService;

    @GetMapping("/updatetag")
    public String updatetag() {
        Integer i = tagService.updatetag(1);
        return "success";
    }
}