package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.model.AccountExistsByEmailRequest;
import com.g7.ercdataservice.model.ReviewerDetailResponse;
import com.g7.ercdataservice.model.UserInfoUpdateRequest;
import com.g7.ercdataservice.model.UserRoleUpdateRequest;
import com.g7.ercdataservice.service.ReviewerService;
import com.g7.ercdataservice.service.UserInfoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/data/user")
@CrossOrigin(origins = {"https://localhost:3000","https://erc-auth-service.herokuapp.com","https://erc-ruh.live"}, maxAge = 3600, allowCredentials = "true")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ReviewerService reviewerService;

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

    @GetMapping("/all/reviewer")
    // @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY')")
    public ResponseEntity<?> getAllReviewers(){
        List<ReviewerDetailResponse> reviewAssigns = new ArrayList<>();
        try {
            reviewAssigns = reviewerService.getAllReviewer();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<>(reviewAssigns,HttpStatus.OK);
    }

    @PostMapping("/exists")
    public ResponseEntity<?> userExistsByEmail(@RequestBody  AccountExistsByEmailRequest request){
        try {
            JSONObject jsonObject = userInfoService.verifyCoInvestigatorsHaveAccount(request.getEmails());
            return new ResponseEntity<>(jsonObject,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }



}
