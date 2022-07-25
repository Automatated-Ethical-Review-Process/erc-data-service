package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;

import java.util.List;

public interface UserInfoService {

    User save(User user);
    User getById(String id);
    List<User> getAllUserInfo();
    void deleteUserInfo(String id);
    void updateUserInfo(String id, UserInfoUpdateRequest request);
    void updateEmail(String id,String email);
}
