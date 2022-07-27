package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.ReviewAssign;
import com.g7.ercdataservice.enums.ReviewerStatus;

import java.util.List;
import java.util.UUID;

public interface ReviewAssignService {

    void add(String reviewerId, UUID pid,long vid);
    ReviewAssign getByProposalAndReviewer();
    ReviewAssign getByVersionAndReviewer();
    void updateReviewerAssignState(String reviewerId, UUID pid, long vid, ReviewerStatus status);
    void removeReviewerAssign(String reviewerId, UUID pid, long vid);
    ReviewAssign getByReviewerAndProposalAndVersion(String reviewerId, UUID pid, long vid);
}
