package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.exception.UserAlreadyExistException;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;
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
    public User save(User userInfo) {
        if(userInfoRepository.existsUserInfoByIdOrEmail(userInfo.getId(), userInfo.getEmail())){
            throw new UserAlreadyExistException("User already exists");
        }
        return userInfoRepository.save(userInfo);
    }

    @Override
    public User getById(String id) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        return userInfoRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUserInfo() {
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
    public void updateUserInfo(String id, UserInfoUpdateRequest request) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        User userInfo = userInfoRepository.findById(id).get();
        BeanUtils.copyProperties(request,userInfo);
        userInfo.setId(id);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateEmail(String id, String email) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        User userInfo = userInfoRepository.findById(id).get();
        userInfo.setEmail(email);
        userInfoRepository.save(userInfo);
    }
}
