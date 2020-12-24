package com.zzlin.controller;

import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.UserBO;
import com.zzlin.service.UserService;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result regist(@RequestBody UserBO userBO) {
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
        userService.createUser(userBO);
        return Result.ok();
    }

    /**
     * 用户登录
     * @param userBO 登录信息
     * @return 返回结果
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public Result login(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        // 用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.errorMsg("用户名和密码不能为空");
        }
        // 登录
        Users users = userService.queryUserForLogin(username, password);
        if (users == null) {
            return Result.errorMsg("用户名或密码错误");
        }
        return Result.ok(users);
    }
}
