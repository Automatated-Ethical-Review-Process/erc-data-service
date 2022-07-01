package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.EvaluationForm;
import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.DecisionType;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ProposalType;
import com.g7.ercdataservice.enums.VersionStatus;
import com.g7.ercdataservice.repository.EvaluationFormRepository;
import com.g7.ercdataservice.repository.ProposalRepository;
import com.g7.ercdataservice.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Set;

@Service
public class DefaultDataService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private EvaluationFormRepository formRepository;

    //@PostConstruct
    public ResponseEntity<?> test(){
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
                .build();

        proposalRepository.save(proposal);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
