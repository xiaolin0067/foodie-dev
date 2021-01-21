package com.zzlin.service.center;

import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.center.CenterUserBO;

/**
 * @author zlin
 * @date 20210121
 */
public interface CenterUserService {

    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return 用户
     */
    Users queryUserInfo(String userId);


    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param centerUserBO 用户BO
     * @return 用户信息
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}
