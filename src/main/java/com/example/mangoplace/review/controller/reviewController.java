package com.example.mangoplace.review.controller;

import com.example.mangoplace.review.service.reviewService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reivew")
@AllArgsConstructor
public class reviewController {

    private final reviewService service;

    @PostMapping("")
    public void createReview(){

    }

    @PutMapping("{reviewId}")
    public void updateReview(@PathVariable("reviewId") int id){

    }

    @DeleteMapping("{reviewId}")
    public void deleteReview(@PathVariable("reviewId") int id){

    }

}
