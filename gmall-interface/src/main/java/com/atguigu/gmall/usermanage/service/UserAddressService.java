package com.atguigu.gmall.usermanage.service;

import com.atguigu.gmall.bean.UserAddress;

import java.util.List;

public interface UserAddressService {
    //通过ID查询用户地址
    List<UserAddress> getAddressList(String userId);
}
