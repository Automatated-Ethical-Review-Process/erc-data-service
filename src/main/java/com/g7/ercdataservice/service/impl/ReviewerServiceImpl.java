package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Reviewer;
import com.g7.ercdataservice.enums.ERole;
import com.g7.ercdataservice.repository.ReviewerRepository;
import com.g7.ercdataservice.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;
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
    public List<Reviewer> getAllReviewer() {
        return null;
    }

    @Override
    public List<Reviewer> getAllReviewerByType(ERole type) {
        return null;
    }
}
