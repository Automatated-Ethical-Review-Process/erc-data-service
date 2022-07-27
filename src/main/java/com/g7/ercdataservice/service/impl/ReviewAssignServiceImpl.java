package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.ReviewAssign;
import com.g7.ercdataservice.entity.Reviewer;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.ReviewerStatus;
import com.g7.ercdataservice.exception.ReviewerAlreadyAssignedException;
import com.g7.ercdataservice.repository.ReviewerAssignRepository;
import com.g7.ercdataservice.service.ProposalService;
import com.g7.ercdataservice.service.ReviewAssignService;
import com.g7.ercdataservice.service.ReviewerService;
import com.g7.ercdataservice.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewAssignServiceImpl implements ReviewAssignService {

    @Autowired
    private ProposalService proposalService;
    @Autowired
    private VersionService versionService;
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private ReviewerAssignRepository assignRepository;
    @Override
    public void add(String reviewerId, UUID pid,long vid) {
        Proposal proposal = proposalService.getById(pid);
        Version version = versionService.getById(pid,vid);
        Reviewer reviewer = reviewerService.getById(reviewerId);

        if(assignRepository.existsReviewAssignByProposalAndReviewerAndVersion(proposal,reviewer,version)) {
            throw new ReviewerAlreadyAssignedException(reviewer.getId() + " is already assigned this version");
        }
        ReviewAssign reviewAssign = ReviewAssign.builder()
                .reviewer(reviewer)
                .proposal(proposal)
                .version(version)
                .status(ReviewerStatus.PENDING)
                .build();
        assignRepository.save(reviewAssign);
    }

    @Override
    public ReviewAssign getByProposalAndReviewer() {
        return null;
    }

    @Override
    public ReviewAssign getByVersionAndReviewer() {
        return null;
    }


    @Override
    public void updateReviewerAssignState(String reviewerId, UUID pid, long vid, ReviewerStatus status) {
        ReviewAssign reviewAssign = getByReviewerAndProposalAndVersion(reviewerId,pid,vid);
        reviewAssign.setStatus(status);
        assignRepository.save(reviewAssign);
    }

    @Override
    public void removeReviewerAssign(String reviewerId, UUID pid, long vid) {
        ReviewAssign reviewAssign = getByReviewerAndProposalAndVersion(reviewerId,pid,vid);
        assignRepository.delete(reviewAssign);
    }
    @Override
    public ReviewAssign getByReviewerAndProposalAndVersion(String reviewerId, UUID pid, long vid){
        Proposal proposal = proposalService.getById(pid);
        Version version = versionService.getById(pid,vid);
        Reviewer reviewer = reviewerService.getById(reviewerId);
        return assignRepository.findReviewAssignByReviewerAndVersionAndProposal(
                reviewer,version,proposal
        ).orElseThrow(
                ()-> new EntityNotFoundException("reviewAssign object not found")
        );
    }

}
