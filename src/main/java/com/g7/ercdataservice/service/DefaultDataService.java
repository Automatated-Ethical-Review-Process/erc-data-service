package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.EvaluationForm;
import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.DecisionType;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ProposalType;
import com.g7.ercdataservice.enums.VersionStatus;
import com.g7.ercdataservice.repository.EvaluationFormRepository;
import com.g7.ercdataservice.repository.ProposalRepository;
import com.g7.ercdataservice.repository.UserInfoRepository;
import com.g7.ercdataservice.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.*;

@Service
public class DefaultDataService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private EvaluationFormRepository formRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    //@PostConstruct
    public ResponseEntity<?> test(){
        User userInfo = userInfoRepository.findById("5f631b24-9f24-49b2-b71f-7327877826a3").get();
        Version version = Version.builder()
                .number(1)
                .status(VersionStatus.GRANTED)
                .comment("Hello Comment sample")
                .build();
        Proposal proposal = Proposal.builder()
                .name("Hello Proposal")
                .date(Instant.now())
                .status(ProposalStatus.ACTIVE)
                .type(ProposalType.HUMAN_RESEARCH)
                .versions(Set.of(version))
                .user(userInfo)
                .build();

        proposalRepository.save(proposal);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public void addUser() throws MalformedURLException {
        List<String> qualification = new ArrayList<>();
        qualification.add("qualification 01");
        qualification.add("qualification 02");
        qualification.add("qualification 03");
        qualification.add("qualification 04");

        User userInfo = User.builder()
                .id(UUID.randomUUID().toString())
                .email("abc@gmail.com")
                .name("Sandaruwan Lakshitha")
                .address("Brookside waththa, Mahagama")
                .landNumber("0341234567")
                .mobileNumber("0711234567")
                .isUnderGraduate(true)
                .nic("970273697V")
                .passport("789456123pass")
                .IdImg(new URL("https://www.google.com"))
                .occupation("Lecturer")
                .position("Student")
                .university("UOR")
                .faculty("Science")
                .year("2019")
                .registrationNumber("SC/2018/10663")
                .educationalQualifications(qualification)
                .build();

        userInfoRepository.save(userInfo);
    }

    //@PostConstruct
    public void addEvaluationForm() throws MalformedURLException {
        Version version = versionRepository.findById(2L).get();
        EvaluationForm form = EvaluationForm.builder()
                .decision(DecisionType.APPROVED)
                .reviewerId("asfwesda")
                .url(new URL("http://www.abc.fdfdfdff"))
                .version(version)
                .build();

        formRepository.save(form);
       System.out.println(version);
    }

}
