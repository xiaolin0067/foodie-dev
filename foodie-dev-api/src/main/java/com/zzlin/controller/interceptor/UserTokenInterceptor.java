package com.zzlin.controller.interceptor;

import com.zzlin.enums.CacheKey;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.RedisOperator;
import com.zzlin.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 用户token校验拦截器
 * @author zlin
 * @date 20210621
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserTokenInterceptor.class);

    @Autowired
    RedisOperator redisOperator;

    /**
     * 拦截请求，在调用controller之前
     * return true-放行, false-拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        if (isBlankOrUndefined(userId) || isBlankOrUndefined(userToken)) {
            LOGGER.info("用户ID或用户Token为空, userId={}, userToken={}", userId, userToken);
            responseObjectJson(response, Result.errorMsg("用户ID或用户Token为空"));
            return false;
        }
        String cacheToken = redisOperator.get(CacheKey.USER_TOKEN.append(userId));
        if (!userToken.equals(cacheToken)) {
            LOGGER.info("请求Token不匹配，请重新登录, userId={}, userToken={}, cacheToken={}", userId, userToken, cacheToken);
            responseObjectJson(response, Result.errorMsg("请求Token不匹配，请重新登录"));
            return false;
        }
        return true;
    }

    private boolean isBlankOrUndefined(String param) {
        return StringUtils.isBlank(param) || "undefined".equals(param);
    }

    private void responseObjectJson(HttpServletResponse response, Object obj) {
        if (Objects.isNull(response) || Objects.isNull(obj)) {
            return;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try (OutputStream os = response.getOutputStream()) {
            String result = JsonUtils.objectToJson(obj);
            os.write( (StringUtils.isBlank(result) ? "" : result).getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            LOGGER.error("响应对象JSON失败", e);
        }
    }

    /**
     * 请求controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 请求controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
