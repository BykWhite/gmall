package com.atguigu.gmall.usermanage.service;

import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> findAll();

    List<UserInfo> findLikeUserInfo();

    void addUserInfo(UserInfo userInfo);

    void upd(UserInfo userInfo);

    void upd1(UserInfo userInfo);

    void del(UserInfo userInfo);

    List<UserInfo> getUserInfoList();
}
