package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.Version;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VersionService {

    Version CreateVersion(MultipartFile main, MultipartFile[] supplementary);
    Version saveByProposal(UUID proposalId, MultipartFile main, MultipartFile[] supplementary);
    Version getById(Proposal proposal,String id);
    void deleteById(Proposal proposal,String id);
    void update (Version version,String id);
}
