//package com.example.mangoplace.domain.review.controller;
//
//import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
//import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
//import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
//import com.example.mangoplace.domain.review.dto.response.UserReviewListReponse;
//import com.example.mangoplace.domain.review.service.ReviewService;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/review")
//@AllArgsConstructor
//public class ReviewController {
//    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);
//    private final ReviewService reviewService;
//
//    @GetMapping
//    public ResponseEntity<List<UserReviewListReponse>> getUserReviews() throws Exception {
//        List<UserReviewListReponse> userReviewListReponses = reviewService.getUserReviews();
//        return ResponseEntity.ok(userReviewListReponses);
//
//    }
//
//    //유저만 작성 가능
//    @PostMapping("")
//    public ResponseEntity<Void> createReview(@RequestBody CreateReviewRequest reviewRequest) throws Exception{
//        reviewService.createReview(reviewRequest);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    //create 한 유저만 수정 가능
//    @PutMapping("/{reviewId}")
//    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewRequest updateReviewRequest) throws Exception{
//        reviewService.updateReview(reviewId, updateReviewRequest);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    //create 한 유저만 삭제 가능
//    @DeleteMapping("/{reviewId}")
//    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) throws Exception{
//        reviewService.deleteReview(reviewId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
