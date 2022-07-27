package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ReviewerStatus;

import java.util.List;
import java.util.UUID;

public interface ProposalService {

    Proposal save(Proposal proposal, Version version);
    Proposal getById(UUID id);
    List<Proposal> getProposalByUser(User userInfo);
    Proposal getProposalByUserAndProposalId(User userInfo, UUID id);
    void deleteById(UUID id);
    void updateProposal(Proposal proposal);
    List<Proposal> getAllProposals();
    void updateProposalState(UUID id, ProposalStatus status);
    List<Proposal> getAllProposalReviewerAndReviewStatus(String reviewerId, ReviewerStatus status);
}
