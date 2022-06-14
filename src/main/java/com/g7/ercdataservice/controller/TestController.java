package com.g7.ercdataservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/test")
public class TestController {

    @GetMapping("/message")
    public String message(){
        return "Hello World";
    }

    @GetMapping("/message/secure")
    public String messageSecure(){
        return "Hello World secure";
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/message/secure/method/1")
    public String messageSecureMethod(){
        return "Hello World secure method";
    }

    @PreAuthorize("hasRole('APPLICANT')")
    @GetMapping("/message/secure/method/2")
    public String messageSecureMethod2(){
        return "Hello World secure method";
    }
}
