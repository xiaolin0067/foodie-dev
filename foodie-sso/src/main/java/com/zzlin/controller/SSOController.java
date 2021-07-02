package com.zzlin.controller;

import com.zzlin.enums.CacheKey;
import com.zzlin.pojo.Users;
import com.zzlin.pojo.vo.UsersVO;
import com.zzlin.service.UserService;
import com.zzlin.utils.CookieUtils;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.RedisOperator;
import com.zzlin.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zlin
 * @date 20210628
 */
@Controller
public class SSOController {

    private final static Logger logger = LoggerFactory.getLogger(SSOController.class);

    @Resource
    UserService userService;

    @Resource
    private RedisOperator redisOperator;

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

    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String returnUrl, Model model,
                          HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);

        // 用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("errMsg", "用户名和密码不能为空");
            return "login";
        }

        // 登录
        Users user = userService.queryUserForLogin(username, password);
        if (user == null) {
            model.addAttribute("errMsg", "用户名或密码错误");
            return "login";
        }
        // 缓存会话token，得到VO
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user,usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        redisOperator.set(CacheKey.USER_TOKEN.append(user.getId()), JsonUtils.objectToJson(usersVO));

        return "login";
    }
}
