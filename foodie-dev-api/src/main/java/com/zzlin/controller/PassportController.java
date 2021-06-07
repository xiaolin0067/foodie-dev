package com.zzlin.controller;

import com.zzlin.enums.CacheKey;
import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.ShopCartBO;
import com.zzlin.pojo.bo.UserBO;
import com.zzlin.service.UserService;
import com.zzlin.utils.CookieUtils;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.RedisOperator;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zlin
 * @date 20201219
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Resource
    UserService userService;

    @Resource
    private RedisOperator redisOperator;

    /**
     * 检查用户名是否存在
     * 若参数不带@RequestParam注解，swagger中Parameter content type为application/json
     * @param username 用户名
     * @return 返回结果
     */
    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名", name = "username", dataType = "string", paramType = "query", required = true)
    })
    @GetMapping("/usernameIsExist")
    public Result usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return Result.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return Result.errorMsg("用户名已存在");
        }
        return Result.ok();
    }

    /**
     * 用户注册
     * @param userBO 注册信息
     * @return 返回结果
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public Result regist(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        // 1.用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return Result.errorMsg("用户名和密码不能为空");
        }
        //2.用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return Result.errorMsg("用户名已存在");
        }
        //3.密码长度不能小于6位
        if (password.length() < 6) {
            return Result.errorMsg("密码长度不能小于6位");
        }
        //4.两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return Result.errorMsg("两次密码不一致");
        }
        // 注册
        Users user = userService.createUser(userBO);
        // 登录信息脱敏
        setNullProperty(user);
        //登录信息缓存
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // TODO 1、生成用户TOKEN存入Redis，2、同步购物车
        syncShopCart(request, response, user.getId());

        return Result.ok();
    }

    /**
     * 用户登录
     * @param userBO 登录信息
     * @return 返回结果
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public Result login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        // request uri为nginx代理的location中配置的proxy_pass http://api.tomcats.com;中的域名
        logger.info("Request url: {}", request.getRequestURL().toString());
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        // 用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.errorMsg("用户名和密码不能为空");
        }
        // 登录
        Users user = userService.queryUserForLogin(username, password);
        if (user == null) {
            return Result.errorMsg("用户名或密码错误");
        }
        // 登录信息脱敏
        setNullProperty(user);
        // 登录信息缓存
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // TODO 1、生成用户TOKEN存入Redis，2、同步购物车
        syncShopCart(request, response, user.getId());

        return Result.ok(user);
    }

    /**
     * 同步缓存与cookie中的购物车列表
     * @param request request
     * @param response response
     * @param userId 用户ID
     */
    public void syncShopCart(HttpServletRequest request, HttpServletResponse response, String userId){
        String shopCartCacheKey = CacheKey.SHOP_CART.append(userId);
        // 获取当前缓存和cookie中的购物车json字符串
        String cacheJson = redisOperator.get(shopCartCacheKey);
        String cookieJson = CookieUtils.getCookieValue(request, CacheKey.SHOP_CART.value, true);
        // 转换为列表
        List<ShopCartBO> cacheList = StringUtils.isBlank(cacheJson) ?
                null : JsonUtils.jsonToList(cacheJson, ShopCartBO.class);
        List<ShopCartBO> cookieList = StringUtils.isBlank(cookieJson) ?
                null : JsonUtils.jsonToList(cookieJson, ShopCartBO.class);
        // 缓存与cookie中购物车列表是否有商品
        boolean itemInCache = !CollectionUtils.isEmpty(cacheList);
        boolean itemInCookie = !CollectionUtils.isEmpty(cookieList);

        if (itemInCache && itemInCookie){
            // 缓存与cookie购物车列表都有商品，删除缓存中规格ID在cookie中存在的商品，以cookie中的商品为准
            List<String> cookieSpecIds = cookieList.stream().map(ShopCartBO::getSpecId).collect(Collectors.toList());
            cacheList.removeIf(shopCart -> cookieSpecIds.contains(shopCart.getSpecId()));
            // 合并购物车列表
            cacheList.addAll(cookieList);
            String mergeJson = JsonUtils.objectToJson(cacheList);
            CookieUtils.setCookie(request, response, CacheKey.SHOP_CART.value, mergeJson, true);
            redisOperator.set(shopCartCacheKey, mergeJson);
        }else if (itemInCache) {
            // 缓存中有商品而cookie中没有商品
            CookieUtils.setCookie(request, response, CacheKey.SHOP_CART.value, cacheJson, true);
        }else if (itemInCookie) {
            // cookie中有商品而缓存中没有商品
            redisOperator.set(shopCartCacheKey, cookieJson);
        }
        // cookie与缓存中都没有商品，无需处理
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登陆", notes = "用户退出登陆", httpMethod = "POST")
    public Result logout(String userId, HttpServletRequest request, HttpServletResponse response) {
        // 清除用户相关cookie
        CookieUtils.deleteCookie(request, response, "user");

        // 用户退出登录，需要清空购物车
        // 分布式会话中需要清除用户数据

        return Result.ok();
    }

    private void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
    }
}
