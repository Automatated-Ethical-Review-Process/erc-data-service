package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.enums.ReviewerStatus;
import com.g7.ercdataservice.service.ReviewAssignService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/data/proposal/{pid}/version/{vid}/reviewer")
public class ReviewAssignController {

    @Autowired
    private ReviewAssignService assignService;

    @PostMapping("/assign")
    public ResponseEntity<?> assignReviewer(@PathVariable UUID pid, @PathVariable long vid, @RequestBody JSONObject jsonObject){
        String reviewerId = jsonObject.getAsString("reviewerId");
        //System.out.println(reviewerId);
        assignService.add(reviewerId,pid,vid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/assign")
    public ResponseEntity<?> unAssignReviewer(@PathVariable UUID pid, @PathVariable long vid, @RequestBody JSONObject jsonObject){
        String reviewerId = jsonObject.getAsString("reviewerId");
        assignService.removeReviewerAssign(reviewerId,pid,vid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/confirm")
    public ResponseEntity<?> updateReviewerAssignStateConfirm(@PathVariable UUID pid, @PathVariable long vid, @RequestBody JSONObject jsonObject){
        String reviewerId = jsonObject.getAsString("reviewerId");
        assignService.updateReviewerAssignState(reviewerId,pid,vid, ReviewerStatus.CONFIRM);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reject")
    public ResponseEntity<?> updateReviewerAssignStateReject(@PathVariable UUID pid, @PathVariable long vid, @RequestBody JSONObject jsonObject){
        String reviewerId = jsonObject.getAsString("reviewerId");
        assignService.updateReviewerAssignState(reviewerId,pid,vid, ReviewerStatus.REJECT);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
