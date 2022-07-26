package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Documents;
import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.DocumentType;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.VersionStatus;
import com.g7.ercdataservice.repository.VersionRepository;
import com.g7.ercdataservice.service.ProposalService;
import com.g7.ercdataservice.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private ProposalService proposalService;

    @Override
    public Version CreateVersion(MultipartFile proposal, MultipartFile[] supplementary) {
        Set<Documents> documentsSet = new HashSet<>();

        Version version = Version.builder()
                .number(1)
                .status(VersionStatus.PENDING)
                .build();

        Documents proposalFile = new Documents();
        proposalFile.setFile(proposal.getOriginalFilename());
        proposalFile.setType(DocumentType.PROPOSAL);
        proposalFile.setVersion(version);
        documentsSet.add(proposalFile);

        for (MultipartFile file:supplementary) {
            documentsSet.add(new Documents(DocumentType.SUPPLEMENTARY,file.getOriginalFilename(),version));
        }
        version.setDocuments(documentsSet);
        return version;
    }

    @Override
    public Version saveByProposal(UUID pid, MultipartFile main, MultipartFile[] supplementary) {
        try {
            Proposal proposal = proposalService.getById(pid);
            Version version = CreateVersion(main,supplementary);
            version.setNumber(versionRepository.countVersionByProposal(proposal)+1);
            version.setProposal(proposal);
            proposal.getVersions().add(version);
            proposalService.updateProposal(proposal);
            return null;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Version getById(UUID pid, long vid) {
        Proposal proposal = proposalService.getById(pid);
        for (Version version:proposal.getVersions()) {
            if(version.getId() == vid){
                return version;
            }
        }
        throw new EntityNotFoundException("pid = "+pid+" vid = "+ vid+" not found");
    }

    @Override
    public void deleteById(Proposal proposal, String id) {

    }

    @Override
    public void update(Version version, String id) {

    }

    @Override
    public void updateVersionState(UUID pid, long vid, VersionStatus status) {
        Proposal proposal = proposalService.getById(pid);
        proposal.getVersions().stream().forEach(
                (x)->{
                    if(x.getId() == vid){
                        x.setStatus(status);
                        if(VersionStatus.SUBMITTED == status)
                        {
                            proposal.setStatus(ProposalStatus.ACTIVE);
                        } else if (VersionStatus.REJECTED == status) {
                            proposal.setStatus(ProposalStatus.REJECTED);
                        } else if (VersionStatus.GRANTED == status) {
                            proposal.setStatus(ProposalStatus.GRANTED);
                        }
                    }
                }
        );
        proposalService.updateProposal(proposal);
    }

}
