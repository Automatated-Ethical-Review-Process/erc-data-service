package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.EReviewType;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ReviewerStatus;
import com.g7.ercdataservice.exception.ProposalOngoingException;
import com.g7.ercdataservice.repository.ProposalRepository;
import com.g7.ercdataservice.repository.ReviewerAssignRepository;
import com.g7.ercdataservice.service.ProposalService;
import com.g7.ercdataservice.service.ReviewAssignService;
import com.g7.ercdataservice.service.ReviewerService;
import com.g7.ercdataservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
public class ProposalServiceImpl implements ProposalService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private ReviewerAssignRepository assignRepository;
    @Autowired
    private ReviewerService reviewerService;
    @Override
    public Proposal save(Proposal proposal, Version version) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userInfoService.getById(authentication.getPrincipal().toString());
        if(proposalRepository.countProposalsByStatusAndUser(ProposalStatus.ACTIVE,user) >=1){
            throw new ProposalOngoingException();
        }
        Set<Version> versionSet = new HashSet<>();

        proposal.setUser(user);
        proposal.setStatus(ProposalStatus.PENDING);
        proposal.setDate(Instant.now());

        version.setProposal(proposal);
        versionSet.add(version);
        proposal.setVersions(versionSet);
        return proposalRepository.save(proposal);
    }

    public void updateProposal(Proposal proposal){
        proposalRepository.save(proposal);
    }

    @Override
    public Proposal getById(UUID id) {
        return proposalRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Proposal not exists")
        );
    }

    @Override
    public List<Proposal> getProposalByUser(User userInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userInfoService.getById(authentication.getPrincipal().toString());
        return proposalRepository.findProposalByUser(user);
    }

    @Override
    public Proposal getProposalByUserAndProposalId(User userInfo, UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userInfoService.getById(authentication.getPrincipal().toString());
        return proposalRepository.findProposalByUserAndId(user,id).orElseThrow(
                ()-> new EntityNotFoundException("Proposal not exists")
        );
    }

    @Override
    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    @Override
    public void deleteById(UUID id){
        Proposal proposal =proposalRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Proposal not exists"));

        proposal.getVersions().clear();
        proposal.setUser(null);
        proposalRepository.deleteById(proposalRepository.save(proposal).getId());
    }

    public List<Proposal> getAllProposalReviewerAndReviewStatus(String reviewerId, ReviewerStatus status) {
        List<Proposal> proposalList = new ArrayList<>();
        assignRepository.findReviewAssignsByReviewerAndStatus(reviewerService.getById(reviewerId),status)
                .orElseThrow(
                        ()-> new EntityNotFoundException("ReviewAssign object not found")
                ).stream().forEach(
                        (x)->{
                            if(!proposalList.contains(x.getProposal())){
                                proposalList.add(x.getProposal());
                            }

                        }
                );
        return proposalList;
    }

    @Override
    public void updateProposalReviewType(UUID pid, EReviewType reviewType) {
        Proposal proposal = getById(pid);
        proposal.setReviewType(reviewType);
        proposalRepository.save(proposal);
    }
}
