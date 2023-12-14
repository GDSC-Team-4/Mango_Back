package com.example.mangoplace.domain.reviewimage.controller;

import com.example.mangoplace.domain.reviewimage.dto.request.UploadImageRequest;
import com.example.mangoplace.domain.reviewimage.service.ReviewImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ReviewImageController {

    private final ReviewImageService reviewImageService;

    @PostMapping("/upload/{reviewId}")
    public ResponseEntity<Void> updateMemberInfo(UploadImageRequest dto, @PathVariable Long reviewId) throws IOException {

        reviewImageService.uploadImage(reviewId, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


