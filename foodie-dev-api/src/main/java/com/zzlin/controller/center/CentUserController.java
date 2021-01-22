package com.zzlin.controller.center;

import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.center.CenterUserBO;
import com.zzlin.service.center.CenterUserService;
import com.zzlin.utils.CookieUtils;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zlin
 * @date 20210121
 */
@Api(value = "用户中信息相关接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CentUserController {

    @Resource
    private CenterUserService centerUserService;

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    @PostMapping("update")
    public Result update(@ApiParam(name = "userId", value = "用户ID", required = true)
                         @RequestParam String userId,
                         @Valid @RequestBody CenterUserBO centerUserBO,
                         BindingResult bindingResult,
                         HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg("用户ID为空");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = getErrors(bindingResult);
            return Result.errorMap(errorMap);
        }

        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return Result.ok();
    }

    private Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> resultMap = new HashMap<>();
        if (bindingResult == null) {
            return resultMap;
        }
        for (FieldError error : bindingResult.getFieldErrors()) {
            String field = error.getField();
            String errorMsg = error.getDefaultMessage();
            resultMap.put(field, errorMsg);
        }
        return resultMap;
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
