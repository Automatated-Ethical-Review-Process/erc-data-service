package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.UserInfo;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;

import java.util.List;

public interface UserInfoService {

    UserInfo save(UserInfo userInfo);
    UserInfo getById(String id);
    List<UserInfo> getAllUserInfo();
    void deleteUserInfo(String id);
    void updateUserInfo(String id, UserInfoUpdateRequest request);
    void updateEmail(String id,String email);
}
