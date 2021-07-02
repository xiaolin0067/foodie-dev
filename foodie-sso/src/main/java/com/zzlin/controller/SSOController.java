package com.zzlin.controller;

import com.zzlin.enums.CacheKey;
import com.zzlin.pojo.Users;
import com.zzlin.pojo.vo.UsersVO;
import com.zzlin.service.UserService;
import com.zzlin.utils.*;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
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

    /**
     * CAS统一登录接口
     * 1.登录后创建用户的全局会话                   ->  uniqueToken
     * 2.创建用户的全局门票，用于标识在CAS段是否登录   ->  userTicket
     * 3.创建用户临时票据，用于会跳回传              ->  tmpTicket
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String returnUrl, Model model,
                          HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);

        // 用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("errMsg", "用户名和密码不能为空");
            return "login";
        }

        // 1.登录
        Users user = userService.queryUserForLogin(username, password);
        if (user == null) {
            model.addAttribute("errMsg", "用户名或密码错误");
            return "login";
        }
        // 2.缓存会话token，得到VO
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user,usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        redisOperator.set(CacheKey.USER_TOKEN.append(user.getId()), JsonUtils.objectToJson(usersVO));

        // 3.生成全局门票ticket，代表用户在CAS平台登陆过，并存放在cookie中
        String userTicket = UUID.randomUUID().toString().trim();
        setCookie(CacheKey.USER_TICKET_COOKIE.value, userTicket, response);

        // 4.userTicket关联用户ID放入缓存，用户有全局门票了
        redisOperator.set(CacheKey.USER_TICKET.append(userTicket), user.getId());

        // 5.生成临时票据回跳到调用端网站，时CAS端签发的一个一次性ticket
        String tmpTicket = createAndCacheTmpTicket();

        return "login";
//        return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
    }

    /**
     * 创建并缓存临时门票
     * @return 临时门票
     */
    private String createAndCacheTmpTicket() {
        String tmpTicket = UUID.randomUUID().toString().trim();
        try {
            redisOperator.set(CacheKey.USER_TMP_TICKET.append(tmpTicket), MD5Utils.getMd5Str(tmpTicket), 600);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }
        return tmpTicket;
    }

    private void setCookie(String key, String val, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, val);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
