package com.zzlin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zlin
 * @date 20210628
 */
@Controller
public class SSOController {

    @GetMapping("/sso/hello")
    @ResponseBody
    public String hello() {
        return "SSO Hello";
    }

    @GetMapping("/login")
    public String login(String returnUrl, Model model,
                        HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);
        model.addAttribute("errMsg", "");

        // TODO 后续完善校验是否登录

        //  用户从未登录，跳转到CAS统一登录页面
        return "login";
    }

}
