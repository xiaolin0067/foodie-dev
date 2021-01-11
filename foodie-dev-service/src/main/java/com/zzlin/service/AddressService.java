package com.zzlin.service;

import com.zzlin.pojo.UserAddress;
import com.zzlin.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author zlin
 * @date 20201225
 */
public interface AddressService {

    /**
     * 查询用户所有收货地址
     * @param userId 用户ID
     * @return 收货地址列表
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO 地址BO
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     * @param addressBO 地址BO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     * @param userId 用户ID
     * @param addressId 地址ID
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 修改默认地址
     * @param userId 用户ID
     * @param addressId 地址ID
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     * @param userId 用户ID
     * @param addressId 地址ID
     * @return 地址信息
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
