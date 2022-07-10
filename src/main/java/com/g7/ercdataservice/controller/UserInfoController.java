package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.entity.UserInfo;
import com.g7.ercdataservice.service.impl.UserInfoServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/data/user")
public class UserInfoController {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @GetMapping("")
    public ResponseEntity<?> getUserInfoByIdSelf(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication.getPrincipal().toString());
            return new ResponseEntity<>(userInfoService.getById(authentication.getPrincipal().toString()),HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY')")
    public ResponseEntity<?> getUserInfoById(@PathVariable String id){
        try {
            return new ResponseEntity<>(userInfoService.getById(id),HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createUserInfo(@RequestBody @Valid  UserInfo userInfo){
        try {
            userInfoService.save(userInfo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody JSONObject jsonObject){
        try {
            String email = jsonObject.getAsString("email");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String id = authentication.getPrincipal().toString();
            userInfoService.updateEmail(id,email);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }



}
