package com.example.mangoplace.domain.review.controller;

import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.domain.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.domain.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable String restaurantId
    ) {
        List<ReviewResponse> responses = reviewService.getShopReviews(restaurantId);
        return ResponseEntity.ok(responses);
    }


    @PostMapping("/{restaurantId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CreateReviewResponse> createReview(
            @PathVariable String restaurantId,
            @ModelAttribute CreateReviewRequest reviewWithImageRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // 사용자가 인증되어 있지 않은 경우
            // 예외 처리 또는 다른 처리 방법을 선택
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 파일 개수 확인
        reviewWithImageRequest.setRestaurantId(restaurantId);

        try {
            CreateReviewResponse response = reviewService.createReviewWithImages(reviewWithImageRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            // 파일이 제대로 받아졌는지 확인
            if (reviewWithImageRequest.getImages() == null || reviewWithImageRequest.getImages().isEmpty()) {
                // 파일이 없는 경우 에러 응답
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            // 이미지 업로드 중 오류 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PutMapping("/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UpdateReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @ModelAttribute UpdateReviewRequest updateReviewRequest,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // 사용자가 인증되어 있지 않은 경우
            // 예외 처리 또는 다른 처리 방법을 선택
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            UpdateReviewResponse response = reviewService.updateReview(reviewId, updateReviewRequest, images);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            // 이미지 업로드 중 오류 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeleteReviewResponse> deleteReview(@PathVariable Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // 사용자가 인증되어 있지 않은 경우
            // 예외 처리 또는 다른 처리 방법을 선택
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        DeleteReviewResponse response = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(response);
    }
}
