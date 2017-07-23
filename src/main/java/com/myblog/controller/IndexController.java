package com.myblog.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Zephery on 2017/6/24.
 */
@Controller
@RequestMapping("/admin")
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/postcomment")
    public String postcomment(HttpServletRequest request) {
        String string = "";
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(string).getAsJsonObject();
        JsonArray array = jsonObject.get("comments").getAsJsonArray();
        return String.valueOf(array.size());
    }

    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request){
//        List<CommentVo> comments = siteService.recentComments(5);
//        List<ContentVo> contents = siteService.recentContents(5);
//        StatisticsBo statistics = siteService.getStatistics();
        // 取最新的20条日志
//        List<LogVo> logs = logService.getLogs(1, 5);
//
//        request.setAttribute("comments", comments);
//        request.setAttribute("articles", contents);
//        request.setAttribute("statistics", statistics);
//        request.setAttribute("logs", logs);
        return "admin/index";
    }
    @RequestMapping("mytest")
    public ModelAndView test(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }
}
