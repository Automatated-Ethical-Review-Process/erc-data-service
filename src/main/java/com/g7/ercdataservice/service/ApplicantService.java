package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Applicant;
import com.g7.ercdataservice.entity.Proposal;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Set;

public interface ApplicantService {

    Applicant save(String id);
    Applicant getById(String id);
    List<Applicant> getAll();
    void delete(String id);
    Set<Proposal> getProposalByApplicantAsCoInvestigator(String id);
    void updateProposal(String[] id,Proposal proposal);
    void removeProposal(String id,Proposal proposal);
}
