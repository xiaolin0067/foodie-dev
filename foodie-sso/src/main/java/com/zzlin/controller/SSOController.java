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
                        HttpServletRequest request) throws NoSuchAlgorithmException {
        model.addAttribute("returnUrl", returnUrl);
        model.addAttribute("errMsg", "");

        String cookieTicket = CookieUtils.getCookieValue(request, CacheKey.USER_TICKET_COOKIE.value);
        if (verifyTicket(cookieTicket)) {
            // 生成临时票据回跳到调用端网站，时CAS端签发的一个一次性ticket，后续验证此tmpTicket，验证通过说明cookie存在全局Ticket
            String tmpTicket = createAndCacheTmpTicket();
            return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
        }
        //  用户未登录，跳转到CAS统一登录页面
        return "login";
    }

    /**
     * 校验ticket是否合法
     */
    private boolean verifyTicket(String cookieTicket) {
        if (StringUtils.isBlank(cookieTicket)) {
            return false;
        }
        String cacheUserId = redisOperator.get(CacheKey.USER_TICKET.append(cookieTicket));
        if (StringUtils.isBlank(cacheUserId)) {
            logger.debug("ticket在缓存中找不到, cookieTicket: {}", cookieTicket);
            return false;
        }
        String userVoJson = redisOperator.get(CacheKey.USER_SESSION.append(cacheUserId));
        if (StringUtils.isBlank(userVoJson)) {
            logger.debug("通过userId在缓存中找不到用户会话, userId: {}", cacheUserId);
            return false;
        }
        return true;
    }

    /**
     * CAS统一登录接口
     * 1.登录后创建用户的全局会话                   ->  uniqueToken
     * 2.创建用户的全局门票，用于标识在CAS段是否登录   ->  userTicket
     * 3.创建用户临时票据，用于会跳回传              ->  tmpTicket
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String returnUrl, Model model,
                          HttpServletResponse response) throws NoSuchAlgorithmException {
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
        redisOperator.set(CacheKey.USER_SESSION.append(user.getId()), JsonUtils.objectToJson(usersVO));
        // 3.生成全局门票ticket，代表用户在CAS平台登陆过，并存放在cookie中
        String userTicket = UUID.randomUUID().toString().trim();
        setCookie(CacheKey.USER_TICKET_COOKIE.value, userTicket, response);
        // 4.userTicket关联用户ID放入缓存，用户有全局门票了
        redisOperator.set(CacheKey.USER_TICKET.append(userTicket), user.getId());
        // 5.生成临时票据回跳到调用端网站，时CAS端签发的一个一次性ticket，后续验证此tmpTicket，验证通过说明cookie存在全局Ticket
        String tmpTicket = createAndCacheTmpTicket();
        return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
    }

    /**
     * 新版Chrome浏览器在跨域请求时不携带cookie导致获取cookie中的ticket失败，通过Firefox验证通过
     */
    @PostMapping("/verifyTmpTicket")
    @ResponseBody
    public Result verifyTmpTicket(String tmpTicket, HttpServletRequest request) throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(tmpTicket)) {
            logger.debug("tmpTicket为空");
            return Result.errorUserTicket("用户票据异常");
        }
        String cacheTmpTicket = redisOperator.get(CacheKey.USER_TMP_TICKET.append(tmpTicket));
        if (StringUtils.isBlank(cacheTmpTicket) || !cacheTmpTicket.equals(MD5Utils.getMd5Str(tmpTicket))) {
            logger.debug("缓存中找不到请求的tmpTicket或验证失败 tmpTicket {},cacheTmpTicket {}", tmpTicket, cacheTmpTicket);
            return Result.errorUserTicket("用户票据异常");
        }
        redisOperator.del(CacheKey.USER_TMP_TICKET.append(tmpTicket));
        String cookieTicket = CookieUtils.getCookieValue(request, CacheKey.USER_TICKET_COOKIE.value);
        if (StringUtils.isBlank(cookieTicket)) {
            logger.debug("验证TmpTicket 在cookies中找不到ticket, cookies: {}", JsonUtils.objectToJson(request.getCookies()));
            return Result.errorUserTicket("用户票据异常");
        }
        String cacheUserId = redisOperator.get(CacheKey.USER_TICKET.append(cookieTicket));
        if (StringUtils.isBlank(cacheUserId)) {
            logger.debug("ticket在缓存中找不到, cookieTicket: {}", cookieTicket);
            return Result.errorUserTicket("用户票据异常");
        }
        String userVoJson = redisOperator.get(CacheKey.USER_SESSION.append(cacheUserId));
        if (StringUtils.isBlank(userVoJson)) {
            logger.debug("通过userId在缓存中找不到用户会话, userId: {}", cacheUserId);
            return Result.errorUserTicket("用户票据异常");
        }
        return Result.ok(JsonUtils.jsonToPojo(userVoJson, UsersVO.class));
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout(String userId, HttpServletRequest request, HttpServletResponse response) {

        // 移除缓存与cookie中的ticket
        String cookieTicket = CookieUtils.getCookieValue(request, CacheKey.USER_TICKET_COOKIE.value);
        redisOperator.del(CacheKey.USER_TICKET.append(cookieTicket));
        setCookie(CacheKey.USER_TICKET_COOKIE.value, null, 0, response);
        // 移除回话
        redisOperator.del(CacheKey.USER_SESSION.append(userId));

        return Result.ok();
    }

    /**
     * 创建并缓存临时门票
     * @return 临时门票
     */
    private String createAndCacheTmpTicket() throws NoSuchAlgorithmException {
        String tmpTicket = UUID.randomUUID().toString().trim();
        String md5TmpTicket = MD5Utils.getMd5Str(tmpTicket);
        redisOperator.set(CacheKey.USER_TMP_TICKET.append(tmpTicket), md5TmpTicket, 600);
        return tmpTicket;
    }

    private void setCookie(String key, String val, HttpServletResponse response) {
        setCookie(key, val, 7 * 24 * 60 * 60, response);
    }

    private void setCookie(String key, String val, int expiry, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, val);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
}
