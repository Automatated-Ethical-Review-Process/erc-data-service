package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.enums.VersionStatus;
import com.g7.ercdataservice.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/data/proposal/{pid}/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @PostMapping("")
    public ResponseEntity<?> addVersion(@PathVariable UUID pid,
                                        @RequestParam MultipartFile proposal,
                                        @RequestParam MultipartFile[] supplementary){

        try {
            versionService.saveByProposal(pid, proposal, supplementary);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{vid}")
    public ResponseEntity<?> getVersionById(@PathVariable UUID pid, @PathVariable long vid)
    {
        return new ResponseEntity<>(versionService.getById(pid,vid),HttpStatus.OK);
    }

    @PutMapping("/{vid}/submit")
    public ResponseEntity<?> updateVersionStateToSubmitted(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.SUBMITTED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{vid}/granted")
    public ResponseEntity<?> updateVersionStateToGranted(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.GRANTED);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{vid}/reject")
    public ResponseEntity<?> updateVersionStateToRejected(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.REJECTED);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{vid}/major")
    public ResponseEntity<?> updateVersionStateToMajor(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.MAJOR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{vid}/minor")
    public ResponseEntity<?> updateVersionStateToMinor(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.MINOR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{vid}/under-review")
    public ResponseEntity<?> updateVersionStateToUnderReview(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.UNDER_REVIEW);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{vid}/awaiting-revisions")
    public ResponseEntity<?> updateVersionStateToAwaitingRevisions(@PathVariable UUID pid, @PathVariable long vid){
        versionService.updateVersionState(pid,vid, VersionStatus.AWAITING_REVISIONS);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
