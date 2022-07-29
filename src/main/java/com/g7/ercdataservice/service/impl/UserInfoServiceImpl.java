package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Reviewer;
import com.g7.ercdataservice.entity.Role;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.enums.ERole;
import com.g7.ercdataservice.exception.UserAlreadyExistException;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;
import com.g7.ercdataservice.repository.ReviewerRepository;
import com.g7.ercdataservice.repository.RoleRepository;
import com.g7.ercdataservice.repository.UserInfoRepository;
import com.g7.ercdataservice.service.ReviewerService;
import com.g7.ercdataservice.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;
    @Override
    public User save(User user) {
        if(userInfoRepository.existsUserInfoByIdOrEmail(user.getId(), user.getEmail())){
            throw new UserAlreadyExistException("User already exists");
        }
        User savedUser = userInfoRepository.save(user);
        Role er = roleRepository.findRoleByName(ERole.ROLE_EXTERNAL_REVIEWER);
        Role ir = roleRepository.findRoleByName(ERole.ROLE_INTERNAL_REVIEWER);
        boolean boolEr = user.getRoles().contains(er);
        boolean boolIr = user.getRoles().contains(ir);

        if( boolEr||boolIr ){
            Reviewer reviewer = Reviewer.builder()
                    .id(user.getId())
                    .role(boolEr?er.getName():ir.getName())
                    .build();
            reviewerRepository.save(reviewer);
        }
        return savedUser;
    }

    @Override
    public User getById(String id) {
        if(!userInfoRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        User user =userInfoRepository.findById(id).get();
        return user;
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
        userInfo.setEducationalQualifications(request.getEducationalQualifications());
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

    @Override
    public void updateUserRoles(String id, Set<Role> roles) {
        try {
            User user = getById(id);
            user.setRoles(roles);
            userInfoRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
