package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.UserInfo;

import java.util.List;

public interface ProposalService {

    Proposal save(Proposal proposal);
    Proposal getById(String id);
    List<Proposal> getProposalByUser(UserInfo userInfo);
    Proposal getProposalByUserAndProposalId(UserInfo userInfo,String id);

    List<Proposal> getAllProposals();
}
