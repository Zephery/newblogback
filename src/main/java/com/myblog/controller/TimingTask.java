package com.myblog.controller;

import com.myblog.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Zephery
 * @since 2017/11/10 19:25
 */
@Component
public class TimingTask {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(TimingTask.class);
    @Resource
    private ITagService tagService;

    @Scheduled(cron = "0 30 2 * * *")
    public void executeFileDownLoadTask() {
        Integer i = tagService.updatetag(1);
        logger.info("更新标签云完成");
    }
}