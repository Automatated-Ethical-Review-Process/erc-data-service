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
import com.g7.ercdataservice.service.ApplicantService;
import com.g7.ercdataservice.service.ReviewerService;
import com.g7.ercdataservice.service.UserInfoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
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
    @Autowired
    private ApplicantService applicantService;

    @Override
    public JSONObject verifyCoInvestigatorsHaveAccount(Set<String> emails) {
        Set<String> verifiedAccount = new HashSet<>();
        Set<String> unVerifiedAccount = new HashSet<>();
        for (String email:emails) {
            if(userInfoRepository.existsUserByEmail(email)){
                verifiedAccount.add(email);
            }else{
                unVerifiedAccount.add(email);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("verified",verifiedAccount);
        jsonObject.put("unverified",unVerifiedAccount);
        return jsonObject;
    }
    @Override
    public User save(User user) {
        if(userInfoRepository.existsUserInfoByIdOrEmail(user.getId(), user.getEmail())){
            throw new UserAlreadyExistException("User already exists");
        }
        User savedUser = userInfoRepository.save(user);
        Role er = roleRepository.findRoleByName(ERole.ROLE_EXTERNAL_REVIEWER);
        Role ir = roleRepository.findRoleByName(ERole.ROLE_INTERNAL_REVIEWER);
        Role applicant = roleRepository.findRoleByName(ERole.ROLE_APPLICANT);
        boolean boolEr = user.getRoles().contains(er);
        boolean boolIr = user.getRoles().contains(ir);

        if( boolEr||boolIr ){
            Reviewer reviewer = Reviewer.builder()
                    .id(user.getId())
                    .role(boolEr?er.getName():ir.getName())
                    .build();
            reviewerRepository.save(reviewer);
        }
        if (user.getRoles().contains(applicant)){
            applicantService.save(user.getId());
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
        Reviewer reviewer = reviewerRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("Reviewer not found id = "+id));
        userInfoRepository.deleteById(id);
        applicantService.delete(id);
        reviewerRepository.delete(reviewer);
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
            User savedUser = userInfoRepository.save(user);
            Role er = roleRepository.findRoleByName(ERole.ROLE_EXTERNAL_REVIEWER);
            Role ir = roleRepository.findRoleByName(ERole.ROLE_INTERNAL_REVIEWER);
            Role applicant = roleRepository.findRoleByName(ERole.ROLE_APPLICANT);
            boolean boolEr = savedUser.getRoles().contains(er);
            boolean boolIr = savedUser.getRoles().contains(ir);
            if(!(boolEr || boolIr)){
                Reviewer reviewer = reviewerRepository.findById(savedUser.getId()).
                        orElseThrow(()-> new EntityNotFoundException("Reviewer not found id = "+user.getId()));
                reviewerRepository.delete(reviewer);
            }
            if (!savedUser.getRoles().contains(applicant)){
                applicantService.delete(savedUser.getId());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@PostConstruct
    void test(){
        System.out.println("Starting >>>>>> ");
        String id ="680d8dad-a9d8-4bd8-b3a2-60b95cd68ac6-1475493493";
        User user = getById(id);
        Role er = roleRepository.findRoleByName(ERole.ROLE_EXTERNAL_REVIEWER);
        Role ir = roleRepository.findRoleByName(ERole.ROLE_INTERNAL_REVIEWER);
        boolean boolEr = user.getRoles().contains(er);
        boolean boolIr = user.getRoles().contains(ir);
        if(!(boolEr || boolIr)){
            System.out.println("Role removed");
        }
    }
}
