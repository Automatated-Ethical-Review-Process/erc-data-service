package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.UserInfo;
import com.g7.ercdataservice.exception.UserAlreadyExistException;
import com.g7.ercdataservice.repository.UserInfoRepository;
import com.g7.ercdataservice.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        if(userInfoRepository.existsUserInfoByIdOrEmail(userInfo.getId(), userInfo.getEmail())){
            throw new UserAlreadyExistException("User already exists");
        }
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getById(String id) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        return userInfoRepository.findById(id).get();
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        if(userInfoRepository.count() == 0){
            throw new EntityNotFoundException("User list not found");
        }
        return userInfoRepository.findAll();
    }

    @Override
    public void deleteUserInfo(String id) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        userInfoRepository.deleteById(id);
    }

    @Override
    public void updateUserInfo(String id, UserInfo userInfo) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        UserInfo userInfo1 = userInfoRepository.findById(id).get();
        BeanUtils.copyProperties(userInfo,userInfo);
        userInfo1.setId(id);
        userInfoRepository.save(userInfo1);
    }

    @Override
    public void updateEmail(String id, String email) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        UserInfo userInfo = userInfoRepository.findById(id).get();
        userInfo.setEmail(email);
        userInfoRepository.save(userInfo);
    }
}
