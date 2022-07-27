package com.g7.ercdataservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.EReviewType;
import com.g7.ercdataservice.enums.ReviewerStatus;
import com.g7.ercdataservice.model.ProposalRequest;
import com.g7.ercdataservice.service.ProposalService;
import com.g7.ercdataservice.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/data/proposal")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;
    @Autowired
    private VersionService versionService;

    @PostMapping("")
    public ResponseEntity<?> addProposal(@RequestParam String data,@RequestParam MultipartFile main,@RequestParam MultipartFile[] supplementary){
        try {
            ProposalRequest request = new ObjectMapper().readValue(data,ProposalRequest.class);
            Version version = versionService.CreateVersion(main, supplementary);
            Proposal proposal = Proposal.builder()
                    .name(request.getName())
                    .type(request.getType())
                    .build();
            proposalService.save(proposal,version);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(proposalService.getAllProposals(),HttpStatus.OK);
    }

    @PutMapping("/{id}/review-type/{type}")
    public ResponseEntity<?> updateProposalReviewTypeById(@PathVariable UUID id, @PathVariable EReviewType type){
        proposalService.updateProposalReviewType(id,type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        return new ResponseEntity<>(proposalService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<?> getByIdAllVersions(@PathVariable UUID id){
        return new ResponseEntity<>(proposalService.getById(id).getVersions(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id){
        proposalService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all/reviewer/{rid}/pending")
    public ResponseEntity<?> getAllProposalReviewerAndReviewStatusPending(@PathVariable String rid){
        return new ResponseEntity<>(proposalService.getAllProposalReviewerAndReviewStatus(rid, ReviewerStatus.PENDING),HttpStatus.OK);
    }

    @GetMapping("/all/reviewer/{rid}/reject")
    public ResponseEntity<?> getAllProposalReviewerAndReviewStatusReject(@PathVariable String rid){
        return new ResponseEntity<>(proposalService.getAllProposalReviewerAndReviewStatus(rid, ReviewerStatus.REJECT),HttpStatus.OK);
    }

    @GetMapping("/all/reviewer/{rid}/confirm")
    public ResponseEntity<?> getAllProposalReviewerAndReviewStatusConfirm(@PathVariable String rid){
        return new ResponseEntity<>(proposalService.getAllProposalReviewerAndReviewStatus(rid, ReviewerStatus.CONFIRM),HttpStatus.OK);
    }
}
