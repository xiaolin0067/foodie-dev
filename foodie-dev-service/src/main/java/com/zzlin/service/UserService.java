package com.zzlin.service;

/**
 * @author zlin
 * @date 20201219
 */
public interface UserService {

    /**
     * 判断用户名是否已存在
     * @return true-存在，false-不存在
     */
    boolean queryUsernameIsExist(String username);
}
