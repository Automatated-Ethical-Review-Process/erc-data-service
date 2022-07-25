package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Role;
import com.g7.ercdataservice.enums.ERole;
import com.g7.ercdataservice.repository.RoleRepository;
import com.g7.ercdataservice.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class DefaultDataService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostConstruct
    public void insertRolesToDB() {
        if(roleRepository.findAll().isEmpty() && roleRepository.count() !=6){
            Role role1 = new Role(ERole.ROLE_APPLICANT);
            Role role2 = new Role(ERole.ROLE_INTERNAL_REVIEWER);
            Role role3 = new Role(ERole.ROLE_EXTERNAL_REVIEWER);
            Role role4 = new Role(ERole.ROLE_CLERK);
            Role role5 = new Role(ERole.ROLE_SECRETARY);
            Role role6 = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
            roleRepository.save(role4);
            roleRepository.save(role5);
            roleRepository.save(role6);
            log.info("Inserted user roles to database");
        }else {
            log.info("user roles already exists");
        }
    }

    @PostConstruct
    public void run(){
        System.out.println(roleRepository.findRoleByName(ERole.ROLE_APPLICANT));
        System.out.println(userInfoRepository.getAlla(1));
        System.out.println(userInfoRepository.getUsersByRole(1));
    }

}
