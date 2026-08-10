package com.example.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.service.S3Service;

@RestController
@RequestMapping("/doctor")
public class TestS3Controller {
    private final S3Service s3Service;

    public TestS3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @RequestMapping("/test")
    public String testS3() {
        return "S3 service is working";
    }
    @GetMapping("/upload-document")
    public String uploadDocument(){
        return s3Service.generatePresignedUrlForUpload("Test.pdf", "application/pdf");

    }

}
