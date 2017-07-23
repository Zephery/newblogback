package com.myblog.controller;

import com.myblog.config.WebConst;
import com.myblog.exception.TipException;
import com.myblog.model.Admin;
import com.myblog.model.Bo.RestResponseBo;
import com.myblog.service.IAdminService;
import com.myblog.service.ILogService;
import com.myblog.util.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户后台登录/登出
 * Created by BlueT on 2017/3/11.
 */
@Controller
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class AuthController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ILogService logService;

    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam(required = false) String remeber_me,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        Integer error_count = cache.get("login_error_count");
        try {
//            Admin admin = adminService.login(username, password);
            Admin admin=new Admin();
            admin.setAdminname("jfioe");
            admin.setAdminpasswd("jofei");
            admin.setId(12);
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, admin);
            if (StringUtils.isNotBlank(remeber_me)) {
                TaleUtils.setCookie(response, admin.getId());
            }
//            logService.insertLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), admin.getId());
        } catch (Exception e) {
            error_count = null == error_count ? 1 : error_count + 1;
            if (error_count > 3) {
                return RestResponseBo.fail("您输入密码已经错误超过3次，请10分钟后尝试");
            }
            cache.set("login_error_count", error_count, 10 * 60);
            String msg = "登录失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                logger.error("e", e);
            }
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }
}
