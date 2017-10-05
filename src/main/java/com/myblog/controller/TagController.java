package com.myblog.controller;

import com.myblog.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Zephery on 2017/6/20.
 */
@RestController
public class TagController {
    @Resource
    private ITagService tagService;
    private static final Logger logger= LoggerFactory.getLogger(TagController.class);

    @GetMapping("updatetag")
    public void get() {
        Integer i=tagService.getBlogByTid(1);
        System.out.println();
    }
    @GetMapping("gettag")
    public void gettag(){

    }
    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }
}
