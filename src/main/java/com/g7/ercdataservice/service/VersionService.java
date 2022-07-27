package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.VersionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VersionService {

    Version CreateVersion(MultipartFile main, MultipartFile[] supplementary);
    Version saveByProposal(UUID proposalId, MultipartFile main, MultipartFile[] supplementary);
    Version getById(UUID pid,long vid);
    void deleteById(Proposal proposal,String id);
    void update (Version version,String id);
    void updateByVersion(Version version);
    void updateVersionState(UUID pId,long vId, VersionStatus status);
    void addCommentToVersion(UUID pId,long vId,String comment);
}
