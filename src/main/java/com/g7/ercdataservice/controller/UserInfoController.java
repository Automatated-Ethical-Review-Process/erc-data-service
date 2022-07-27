package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;
import com.g7.ercdataservice.model.UserRoleUpdateRequest;
import com.g7.ercdataservice.service.UserInfoService;
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
@CrossOrigin(origins = {"https://localhost:3000","https://erc-auth-service.herokuapp.com","https://erc-ruh.live"}, maxAge = 3600, allowCredentials = "true")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("")
    public ResponseEntity<?> getUserInfoByIdSelf(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userInfoService.getById(authentication.getPrincipal().toString()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY')")
    public ResponseEntity<?> getUserInfoById(@PathVariable String id){
        return new ResponseEntity<>(userInfoService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/all")
   // @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY')")
    public ResponseEntity<?> getAllUserInfo(){
        return new ResponseEntity<>(userInfoService.getAllUserInfo(),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUserInfo(@RequestBody @Valid User user){
        userInfoService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoUpdateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getPrincipal().toString();
        userInfoService.updateUserInfo(id,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody JSONObject jsonObject){
        String email = jsonObject.getAsString("email");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getPrincipal().toString();
        userInfoService.updateEmail(id,email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/role")
    public ResponseEntity<?> updateRole(@RequestBody UserRoleUpdateRequest request){
       try {
           System.out.println(request);
           userInfoService.updateUserRoles(request.getId(), request.getRoles());
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
