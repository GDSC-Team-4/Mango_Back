package com.example.mangoplace.review.controller;

import com.example.mangoplace.review.dto.ReviewDTO;
import com.example.mangoplace.review.service.ReviewService;
import com.example.mangoplace.signup.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final JwtUtils jwtUtils;

    @GetMapping("")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        ReviewDTO review = reviewService.getReviewById(reviewId);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //유저만 작성 가능
    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> createReview(@RequestBody ReviewDTO reviewDTO) throws Exception{
        reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //create 한 유저만 수정 가능
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) throws Exception{
        reviewService.updateReview(reviewId, reviewDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //create 한 유저만 삭제 가능
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) throws Exception{
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

