package com.example.mangoplace.domain.review.dto.request;


import com.example.mangoplace.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private Double star;
    private String content;

    public Review toEntity(){
        return Review.builder()
                .content(content)
                .star(star)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
