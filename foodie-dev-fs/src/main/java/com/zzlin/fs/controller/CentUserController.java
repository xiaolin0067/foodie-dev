package com.zzlin.fs.controller;

import com.zzlin.enums.CacheKey;
import com.zzlin.enums.ImageFileSuffix;
import com.zzlin.fs.config.FileConfig;
import com.zzlin.fs.service.FdfsService;
import com.zzlin.pojo.Users;
import com.zzlin.pojo.vo.UsersVO;
import com.zzlin.service.center.CenterUserService;
import com.zzlin.utils.CookieUtils;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.RedisOperator;
import com.zzlin.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zlin
 * @date 20210121
 */
@RestController
@RequestMapping("fdfs")
public class CentUserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CentUserController.class);

    @Resource
    private FdfsService fdfsService;

    @Resource
    private FileConfig fileConfig;

    @Resource
    private RedisOperator redisOperator;

    @Resource
    private CenterUserService centerUserService;

    @PostMapping("uploadFace")
    public Result uploadFace(
            String userId,
            MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg("用户ID为空");
        }
        if (file == null || file.isEmpty()) {
            return Result.errorMsg("头像文件为空");
        }
        String uploadedPath = "";
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(originalFilename)) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            if (ImageFileSuffix.illegalSuffix(suffix)) {
                return Result.errorMsg("不合法的文件类型");
            }
            try {
                uploadedPath = fdfsService.upload(file, suffix);
                LOGGER.info("文件上传路径：{}", uploadedPath);
            }catch (Exception e) {
                LOGGER.error("头像上传失败", e);
            }
        }
        if (StringUtils.isBlank(uploadedPath)) {
            return Result.errorMsg("头像上传失败");
        }
        String faceUrl  = fileConfig.getServerUrl() + uploadedPath;
        Users user = centerUserService.updateUserFace(userId, faceUrl);
        // 缓存会话token，得到VO
        UsersVO usersVO = cacheTokenAndConvertVO(user);
        // 踩坑笔记：若访问前端却用的localhost，但前端代码中请求后端的url为为本机的IP而非localhost
        // 将会导致设置cookie到IP上而非localhost，给你一种cookie设置失效的错觉
        CookieUtils.setCookie(request, response, CacheKey.USER.value, JsonUtils.objectToJson(usersVO), true);
        LOGGER.info("用户会话：{}", JsonUtils.objectToJson(usersVO));
        return Result.ok();
    }

    /**
     * 缓存用户会话token并转换为VO
     * @param user 用户
     * @return 用户VO
     */
    public UsersVO cacheTokenAndConvertVO(Users user) {
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(CacheKey.USER_TOKEN.append(user.getId()), uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user,usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
