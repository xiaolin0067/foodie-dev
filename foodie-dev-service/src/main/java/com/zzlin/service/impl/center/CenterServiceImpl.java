package com.zzlin.service.impl.center;

import com.zzlin.mapper.UsersMapper;
import com.zzlin.pojo.Users;
import com.zzlin.pojo.bo.center.CenterUserBO;
import com.zzlin.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zlin
 * @date 20210121
 */
@Service
public class CenterServiceImpl implements CenterUserService {

    @Resource
    private UsersMapper usersMapper;

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }

    /**
     * 更新用户信息
     *
     * @param userId       用户ID
     * @param centerUserBO 用户BO
     * @return 用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users users = new Users();
        users.setId(userId);
        users.setUpdatedTime(new Date());
        BeanUtils.copyProperties(centerUserBO, users);
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserInfo(userId);
    }
}
