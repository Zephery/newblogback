package com.myblog.controller;

import com.myblog.model.Bo.RestResponseBo;
import com.myblog.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Zephery on 2017/6/20.
 */
@RestController
@RequestMapping("/admin/tag")
public class TagController {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);
    @Resource
    private ITagService tagService;


    @GetMapping("/gettag")
    public void gettag() {

    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @PostMapping("/delete")
    public RestResponseBo deleteTag(Integer tId) {
        try {
            tagService.deleteTag(tId);
        } catch (Exception e) {
            logger.error("e", e);
            return RestResponseBo.fail();
        }
        return RestResponseBo.ok();
    }
}
