package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Role;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Set;

public interface UserInfoService {

    User save(User user);
    User getById(String id);
    List<User> getAllUserInfo();
    void deleteUserInfo(String id);
    void updateUserInfo(String id, UserInfoUpdateRequest request);
    void updateEmail(String id,String email);
    void updateUserRoles(String id, Set<Role> roles);
    JSONObject verifyCoInvestigatorsHaveAccount(Set<String> emails);
}
