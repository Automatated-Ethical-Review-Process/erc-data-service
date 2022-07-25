package com.g7.ercdataservice.controller;

import com.g7.ercdataservice.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/data/proposal/{id}/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @PostMapping("")
    public ResponseEntity<?> addVersion(@PathVariable UUID id,
                                        @RequestParam MultipartFile main,
                                        @RequestParam MultipartFile[] supplementary){

        try {
            versionService.saveByProposal(id, main, supplementary);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
