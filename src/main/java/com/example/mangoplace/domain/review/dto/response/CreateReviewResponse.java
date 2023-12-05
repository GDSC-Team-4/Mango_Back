package com.example.mangoplace.domain.review.dto.response;


import com.example.mangoplace.domain.review.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReviewResponse {
    private Long reviewId;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private String content;
    private Integer star;

    public CreateReviewResponse fromEntity(ReviewEntity review){
        return CreateReviewResponse.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .star(review.getStar())
                .build();
    }
}

