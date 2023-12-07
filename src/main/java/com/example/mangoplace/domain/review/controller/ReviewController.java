package com.example.mangoplace.domain.review.controller;

import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.domain.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.domain.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable String restaurantId
    ) {
        List<ReviewResponse> responses = reviewService.getShopReviews(restaurantId);
        return ResponseEntity.ok(responses);
    }


    @PostMapping("/{restaurantId}")
    public ResponseEntity<CreateReviewResponse> createReview(
            @PathVariable String restaurantId,
            @RequestBody CreateReviewRequest createReviewRequest) {
        createReviewRequest.setRestaurantId(restaurantId);
        CreateReviewResponse response = reviewService.createReview(createReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestBody UpdateReviewRequest updateReviewRequest) {
        UpdateReviewResponse response = reviewService.updateReview(reviewId, updateReviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<DeleteReviewResponse> deleteReview(@PathVariable Long reviewId) {
        DeleteReviewResponse response = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(response);
    }
}