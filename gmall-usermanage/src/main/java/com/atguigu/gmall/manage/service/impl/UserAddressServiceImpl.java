package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.manage.mapper.UserAddressMapper;
import com.atguigu.gmall.manage.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> getAddressList(String userId) {
        //创建用户地址对象
        UserAddress userAddress = new UserAddress();
        //将用户的ID传给对象
        userAddress.setUserId(userId);
        //使用通用Mapper查出id星系
        List<UserAddress> listUserAddresses =  userAddressMapper.select(userAddress);
        return listUserAddresses;
    }
}
