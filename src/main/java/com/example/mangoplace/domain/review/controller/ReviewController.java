package com.example.mangoplace.domain.review.controller;

import com.example.mangoplace.domain.auth.security.AuthTokenFilter;
import com.example.mangoplace.domain.auth.security.JwtUtils;
import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.domain.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtils jwtUtils;
    private final AuthTokenFilter authTokenFilter;
    private final ObjectMapper objectMapper;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable String restaurantId
    ) {
        List<ReviewResponse> responses = reviewService.getShopReviews(restaurantId);
        return ResponseEntity.ok(responses);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{restaurantId}")
    public ResponseEntity<CreateReviewResponse> createReview(
            @PathVariable String restaurantId,
            @ModelAttribute CreateReviewRequest reviewWithImageRequest) {

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
    @PreAuthorize("@reviewControllerSecurity.checkReviewOwnership(#reviewId)")
    public ResponseEntity<UpdateReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @ModelAttribute UpdateReviewRequest updateReviewRequest,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {

        try {
            String jwtToken = getJwt();
            String currentUsername = jwtUtils.getUserNameFromJwtToken(jwtToken);

            Review review = reviewService.getReviewById(reviewId);

            if (review != null && currentUsername.equals(review.getUser().getUsername())) {
                UpdateReviewResponse response = reviewService.updateReview(reviewId, updateReviewRequest, images);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (IOException e) {
            // 이미지 업로드 중 오류 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("@reviewControllerSecurity.checkReviewOwnership(#reviewId)")
    public ResponseEntity<DeleteReviewResponse> deleteReview(@PathVariable Long reviewId) {

        String jwtToken = getJwt();
        String currentUsername = jwtUtils.getUserNameFromJwtToken(jwtToken);

        Review review = reviewService.getReviewById(reviewId);

        if (review != null && currentUsername.equals(review.getUser().getUsername())) {
            DeleteReviewResponse response = reviewService.deleteReview(reviewId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        return authTokenFilter.parseJwt(request);
    }
}
