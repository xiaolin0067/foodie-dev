package com.zzlin.controller;

import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.UserBO;
import com.zzlin.service.UserService;
import com.zzlin.utils.CookieUtils;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zlin
 * @date 20201219
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Resource
    UserService userService;

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

        return Result.ok(user);
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
