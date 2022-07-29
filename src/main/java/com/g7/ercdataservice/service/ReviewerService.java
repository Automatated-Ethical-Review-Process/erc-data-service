package com.g7.ercdataservice.service;

import com.g7.ercdataservice.entity.Reviewer;
import com.g7.ercdataservice.enums.ERole;
import com.g7.ercdataservice.model.ReviewerDetailResponse;

import java.util.List;

public interface ReviewerService {

    void add(Reviewer reviewer);
    Reviewer getById(String id);
    void deleteById(String id);
    List<ReviewerDetailResponse> getAllReviewer();
    List<Reviewer> getAllReviewerByType(ERole type);
}
