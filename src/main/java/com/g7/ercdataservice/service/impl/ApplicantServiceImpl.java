package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Applicant;
import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.repository.ApplicantRepository;
import com.g7.ercdataservice.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Override
    public Applicant save(String id) {
        Applicant applicant = Applicant.builder()
                .id(id)
                .build();
        return applicantRepository.save(applicant);
    }

    @Override
    public Applicant getById(String id) {
       return applicantRepository.findById(id)
               .orElseThrow(
                       ()-> new EntityNotFoundException("Applicant not found id "+id)
               );
    }

    @Override
    public List<Applicant> getAll() {
        return applicantRepository.findAll();
    }

    @Override
    public void delete(String id) {
        Applicant applicant = getById(id);
        applicantRepository.delete(applicant);
    }

    @Override
    public Set<Proposal> getProposalByApplicantAsCoInvestigator(String id) {
        Applicant applicant = getById(id);
        return applicant.getProposals();
    }

    @Override
    public void updateProposal(String[] id,Proposal proposal) {
        for (String x:id) {
            Applicant applicant = getById(x);
            applicant.getProposals().add(proposal);
            applicantRepository.save(applicant);
        }
    }

    @Override
    public void removeProposal(String id, Proposal proposal) {
        Applicant applicant = getById(id);
        applicant.getProposals().remove(proposal);
        applicantRepository.save(applicant);
    }
}
