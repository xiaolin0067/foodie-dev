package com.zzlin.controller;

import com.zzlin.service.UserService;
import com.zzlin.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/usernameIsExist")
    public Result usernameIsExist(String username) {
        if (StringUtils.isBlank(username)) {
            return Result.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return Result.errorMsg("用户名已存在");
        }
        return Result.ok();
    }
}
