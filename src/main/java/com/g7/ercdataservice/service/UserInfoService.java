package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo save(UserInfo userInfo);
    UserInfo getById(String id);
    List<UserInfo> getAllUserInfo();
    void deleteUserInfo(String id);
    void updateUserInfo(String id,UserInfo userInfo);
    void updateEmail(String id,String email);
}
