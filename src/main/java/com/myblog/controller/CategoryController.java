package com.myblog.controller;

import com.myblog.exception.TipException;
import com.myblog.model.Bo.RestResponseBo;
import com.myblog.model.Category;
import com.myblog.model.Tag;
import com.myblog.service.ICategoryService;
import com.myblog.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Zephery
 * Time: 2017/7/29 16:24
 * Description:
 */
@Controller
@RequestMapping("admin/category")
public class CategoryController extends BaseController {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private ITagService tagService;
    @Resource
    private ICategoryService categoryService;

    @GetMapping(value = "")
    public ModelAndView index() {
        List<Category> categories = categoryService.getAllCategory();
        List<Tag> tags = tagService.getAllTags();
        ModelAndView mv = new ModelAndView();
        mv.addObject("categories", categories);
        mv.addObject("tags", tags);
        mv.setViewName("admin/category");
        return mv;
    }

    @PostMapping(value = "save")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo saveCategory(@RequestParam String cName, @RequestParam Integer mid) {
        try {
            categoryService.update_category(cName, mid);
        } catch (Exception e) {
            String msg = "分类保存失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                logger.error("error", msg);
            }
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo delete(@RequestParam int mid) {
        try {
            categoryService.delete_category(mid);
        } catch (Exception e) {
            String msg = "删除失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                logger.error("delete error", msg);
            }
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }
}