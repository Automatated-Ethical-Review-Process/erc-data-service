package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.exception.ProposalOngoingException;
import com.g7.ercdataservice.repository.ProposalRepository;
import com.g7.ercdataservice.service.ProposalService;
import com.g7.ercdataservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProposalServiceImpl implements ProposalService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ProposalRepository proposalRepository;
    @Override
    public Proposal save(Proposal proposal, Version version) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userInfoService.getById(authentication.getPrincipal().toString());
        if(proposalRepository.countProposalsByStatusAndUser(ProposalStatus.ACTIVE,user) >=1){
            throw new ProposalOngoingException();
        }
        Set<Version> versionSet = new HashSet<>();
        versionSet.add(version);
        proposal.setVersions(versionSet);
        proposal.setUser(user);
        proposal.setStatus(ProposalStatus.SUBMITTED);
        proposal.setDate(Instant.now());
        version.setProposal(proposal);
        versionSet.add(version);
        proposal.setVersions(versionSet);
        return proposalRepository.save(proposal);
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
}
