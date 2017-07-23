package com.myblog.controller;

import com.myblog.exception.TipException;
import com.myblog.model.Admin;
import com.myblog.service.IAdminService;
import com.myblog.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Zephery on 2017/7/17.
 */
@Controller
@Transactional(rollbackFor = TipException.class)
public class ShiroController {
    private final static Logger logger= LoggerFactory.getLogger(ShiroController.class);
    @Resource
    private IAdminService adminService;

    @GetMapping("login")
    public ModelAndView get_login(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @PostMapping("login")
    public String login(Admin admin, HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            String newPassword = CryptographyUtil.md5(admin.getAdminpasswd(), "test");
            UsernamePasswordToken token = new UsernamePasswordToken(admin.getAdminname(), newPassword);
            subject.login(token);
            return "redirect:/admin/index.html";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("admin", admin);
            request.setAttribute("errorInfo", "LoginWrong");
            return "login";
        } catch (NullPointerException e) {
            logger.error("empty login" + e);
            return "login";
        }
    }
}
