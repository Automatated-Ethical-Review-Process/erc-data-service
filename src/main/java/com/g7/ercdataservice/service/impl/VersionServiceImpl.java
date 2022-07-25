package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Documents;
import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.DocumentType;
import com.g7.ercdataservice.enums.VersionStatus;
import com.g7.ercdataservice.repository.VersionRepository;
import com.g7.ercdataservice.service.ProposalService;
import com.g7.ercdataservice.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Version CreateVersion(MultipartFile main, MultipartFile[] supplementary) {
        Set<Documents> documentsSet = new HashSet<>();

        Version version = Version.builder()
                .number(1)
                .status(VersionStatus.UNDER_REVIEWER)
                .build();

        Documents mainFile = new Documents();
        mainFile.setFile(main.getOriginalFilename());
        mainFile.setType(DocumentType.PROPOSAL);
        mainFile.setVersion(version);
        documentsSet.add(mainFile);

        for (MultipartFile file:supplementary) {
            documentsSet.add(new Documents(DocumentType.SUPPLEMENTARY,file.getOriginalFilename(),version));
        }
        version.setDocuments(documentsSet);
        return version;
    }

    @Override
    public Version saveByProposal(UUID proposalId, MultipartFile main, MultipartFile[] supplementary) {
        Proposal proposal = proposalService.getById(proposalId);
        Version version = CreateVersion(main,supplementary);
        version.setProposal(proposal);
        version.setNumber(versionRepository.countVersionByProposal(proposal));
        return versionRepository.save(version);
    }

    @Override
    public Version getById(Proposal proposal, String id) {
        return null;
    }

    @Override
    public void deleteById(Proposal proposal, String id) {

    }

    @Override
    public void update(Version version, String id) {

    }
}
