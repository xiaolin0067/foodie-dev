package com.zzlin.controller;

import com.zzlin.pojo.bo.UserBO;
import com.zzlin.service.UserService;
import com.zzlin.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20201219
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Resource
    UserService userService;

    /**
     * 若参数不带@RequestParam注解，swagger中Parameter content type为application/json
     * @param username 用户名
     * @return 返回结果
     */
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
}
