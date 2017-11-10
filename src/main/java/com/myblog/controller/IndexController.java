package com.myblog.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Zephery on 2017/6/24.
 */
@Controller
@RequestMapping("/admin")
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/postcomment")
    public String postcomment(HttpServletRequest request) {
        String string = "";
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(string).getAsJsonObject();
        JsonArray array = jsonObject.get("comments").getAsJsonArray();
        return String.valueOf(array.size());
    }

    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        List<String> loginlog = stringRedisTemplate.opsForList().range("loginlog", 0, 10);
        request.setAttribute("loginlog", loginlog);
        return "/admin/index";
    }

    @RequestMapping("/mytest")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }

    @GetMapping("/links")
    public String links() {
        return "admin/links";
    }

    @GetMapping("/setting")
    public String setting() {
        return "/admin/setting";
    }
}
