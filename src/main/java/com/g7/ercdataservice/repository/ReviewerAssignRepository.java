package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.ReviewAssign;
import com.g7.ercdataservice.entity.Reviewer;
import com.g7.ercdataservice.entity.Version;
import com.g7.ercdataservice.enums.ReviewerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ReviewerAssignRepository")
public interface ReviewerAssignRepository extends JpaRepository<ReviewAssign,Long> {

    Optional<ReviewAssign> findReviewAssignByReviewerAndProposal(Reviewer reviewer, Proposal proposal);
    Optional<ReviewAssign> findReviewAssignByReviewerAndVersionAndProposal(Reviewer reviewer, Version version, Proposal proposal);
    Optional<List<ReviewAssign>> findReviewAssignsByReviewerAndStatus(Reviewer reviewer, ReviewerStatus status);
    Optional<List<ReviewAssign>> findReviewAssignsByProposal(Proposal proposal);
    Optional<List<ReviewAssign>> findReviewAssignsByVersion(Version version);
    boolean existsReviewAssignByProposalAndReviewerAndVersion(Proposal proposal, Reviewer reviewer, Version version);

    int countReviewAssignsByReviewerAndStatusNot(Reviewer reviewer, ReviewerStatus status);
    int countReviewAssignsByReviewer(Reviewer reviewer);
}
