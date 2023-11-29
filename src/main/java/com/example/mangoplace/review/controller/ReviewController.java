package com.example.mangoplace.review.controller;

import com.example.mangoplace.review.dto.ReviewDTO;
import com.example.mangoplace.review.service.ReviewService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService service;

    @GetMapping("")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = service.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        ReviewDTO review = service.getReviewById(reviewId);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> createReview(@RequestBody ReviewDTO reviewDTO) {
        service.createReview(reviewDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        service.updateReview(reviewId, reviewDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        service.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

