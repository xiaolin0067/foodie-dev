package com.zzlin.service.impl;

import com.zzlin.mapper.UsersMapper;
import com.zzlin.pojo.Users;
import com.zzlin.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20201219
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UsersMapper usersMapper;

    /**
     * 判断用户名是否已存在
     * @return true-存在，false-不存在
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        int count = usersMapper.selectCountByExample(userExample);
        return count > 0;
    }
}
