package com.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.model.Blog;
import com.myblog.model.Bo.RestResponseBo;
import com.myblog.model.Category;
import com.myblog.service.IBlogService;
import com.myblog.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Zephery on 2017/7/1.
 */
@Controller
@RequestMapping("/admin/article")
public class BlogController {
    private final static Logger logger = LoggerFactory.getLogger(BlogController.class);
    @Resource
    private ICategoryService categoryService;
    @Resource
    private IBlogService blogService;


    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit, HttpServletRequest request) {
        PageHelper.startPage(page, limit);
        List<Blog> lists = blogService.getAllBlog();
        PageInfo<Blog> blogs = new PageInfo<>(lists);
        blogs.setPageSize(limit);
        blogs.setSize(limit);
        blogs.setPages(page);
        request.setAttribute("blogs", blogs);
        return "admin/article_list";
    }

    @GetMapping(value = "/{cid}")
    public ModelAndView editArticle(@PathVariable String cid, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Blog blog = blogService.getBlogDetail(Integer.parseInt(cid));
        modelAndView.addObject("blog", blog);
        List<Category> categories = categoryService.getAllCategory();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("active", "article");
        modelAndView.setViewName("/admin/article_edit");
        return modelAndView;
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public RestResponseBo modifyArticle(Blog blog, HttpServletRequest request) {
        try {
            blogService.updateByPrimaryKeySelective(blog);
        } catch (Exception e) {
            String msg = "文章编辑失败";
            logger.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    @GetMapping("/publish")
    public ModelAndView getpublish() {
        List<Category> categoryList = categoryService.getAllCategory();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categories", categoryList);
        modelAndView.setViewName("/admin/article_edit");
        return modelAndView;
    }

    @PostMapping("/publish")
    @ResponseBody
    public RestResponseBo publishArticle(Blog blog, HttpServletRequest request) {
        try {
            blogService.insertblog(blog);
        } catch (Exception e) {
            String msg = "文章发布失败";
            logger.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    @PostMapping("/delete")
    @ResponseBody
    public RestResponseBo delete(HttpServletRequest request) {
        String blogid_string = request.getParameter("blogid");
        Integer blogid = Integer.parseInt(blogid_string);
        try {
            blogService.delete(blogid);
            return RestResponseBo.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponseBo.fail();
        }
    }
}
