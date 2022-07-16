package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.User;

import java.util.List;

public interface ProposalService {

    Proposal save(Proposal proposal);
    Proposal getById(String id);
    List<Proposal> getProposalByUser(User userInfo);
    Proposal getProposalByUserAndProposalId(User userInfo, String id);

    List<Proposal> getAllProposals();
}
