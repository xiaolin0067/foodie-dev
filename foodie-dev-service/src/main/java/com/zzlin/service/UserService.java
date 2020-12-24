package com.zzlin.service;

import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.UserBO;

/**
 * @author zlin
 * @date 20201219
 */
public interface UserService {

    /**
     * 判断用户名是否已存在
     * @param username 用户名
     * @return true-存在，false-不存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 用户注册
     * @param userBO 前端参数
     * @return 用户
     */
    Users createUser(UserBO userBO);

    /**
     * 用户登录，检查用户名密码是否匹配
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    Users queryUserForLogin(String username, String password);
}
