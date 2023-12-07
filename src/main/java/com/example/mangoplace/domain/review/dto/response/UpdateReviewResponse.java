package com.example.mangoplace.domain.review.dto.response;


import com.example.mangoplace.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewResponse {
    private Long reviewId;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userName;
    private String content;
    private Double star;

    public static UpdateReviewResponse fromEntity(Review review){
        return UpdateReviewResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .updatedAt(review.getUpdatedAt())
                .star(review.getStar())
                .build();
    }
}
