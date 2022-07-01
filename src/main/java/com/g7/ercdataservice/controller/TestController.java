package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.repository.ProposalRepository;
import com.g7.ercdataservice.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/test")
public class TestController {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private VersionRepository versionRepository;
    @GetMapping("")
    public ResponseEntity<?> text() {

//        System.out.println(versionRepository.findById(2L).get());
//        System.out.println(versionRepository.findById(2L).get().getEvaluationForms());
//        Proposal p = proposalRepository.findById(UUID.fromString("7b92b636-f23a-44a4-8c89-c38d9cf36c5a")).get();
//        proposalRepository.delete(p);
        versionRepository.deleteById(3L);
        return new ResponseEntity<>(proposalRepository.findAll(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/message")
    public String message(){
        return "Hello World";
    }

    @GetMapping("/message/secure")
    public String messageSecure(){
        return "Hello World secure";
    }

    //@PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/message/secure/method/1")
    public String messageSecureMethod(){
        return "Hello World secure method";
    }

    //@PreAuthorize("hasRole('APPLICANT')")
    @GetMapping("/message/secure/method/2")
    public String messageSecureMethod2(){
        return "Hello World secure method";
    }


}
