package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.ReviewAssign;
import com.g7.ercdataservice.entity.Reviewer;
import com.g7.ercdataservice.enums.ERole;
import com.g7.ercdataservice.enums.ReviewerStatus;
import com.g7.ercdataservice.model.ReviewerDetailResponse;
import com.g7.ercdataservice.repository.ReviewerAssignRepository;
import com.g7.ercdataservice.repository.ReviewerRepository;
import com.g7.ercdataservice.repository.UserInfoRepository;
import com.g7.ercdataservice.service.ReviewAssignService;
import com.g7.ercdataservice.service.ReviewerService;
import com.g7.ercdataservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;
//    @Autowired
//    private UserInfoRepository userInfoRepository;

    @Autowired
    @Lazy
    private UserInfoService userInfoService;
//    @Autowired
//    private ReviewerAssignRepository assignRepository;

    @Autowired
    @Lazy
    private ReviewAssignService assignService;
    @Override
    public void add(Reviewer reviewer) {
        reviewerRepository.save(reviewer);
    }

    @Override
    public Reviewer getById(String id) {
        return reviewerRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(id+" reviewer is not found")
        );
    }

    @Override
    public void deleteById(String id) {
        Reviewer reviewer = reviewerRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(id+" reviewer is not found")
        );
        reviewerRepository.delete(reviewer);
    }

    @Override
    public List<ReviewerDetailResponse> getAllReviewer() {
        List<Reviewer> reviewerList = reviewerRepository.findAll();
        List<ReviewerDetailResponse> reviewerDetailResponses = new ArrayList<>();
        reviewerList.forEach(
                (x)->{
                    ReviewerDetailResponse response = ReviewerDetailResponse.builder()
                            .reviewerId(x.getId())
                            //.name(userInfoRepository.findById(x.getId()).get().getName())
                            .name(userInfoService.getById(x.getId()).getName())
                            .role(x.getRole())
//                            .assignedProposal(assignRepository.countReviewAssignsByReviewerAndStatusNot(
//                                    x, ReviewerStatus.REJECT)
//                            )
                            .assignedProposal(assignService.countReviewAssignsByReviewerNotStatusOnGoing(
                                    x,ReviewerStatus.REJECT
                            ))
                            .build();
                    reviewerDetailResponses.add(response);
                }
        );
        System.out.println(reviewerDetailResponses);
        return reviewerDetailResponses;
    }

    @Override
    public List<Reviewer> getAllReviewerByType(ERole type) {
        return null;
    }
}
