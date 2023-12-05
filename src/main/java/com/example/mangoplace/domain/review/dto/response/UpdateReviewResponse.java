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
public class UpdateReviewResponse {
    private Long reviewId;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userName;
    private String content;
    private int star;

    public static UpdateReviewResponse fromEntity(ReviewEntity review){
        return UpdateReviewResponse.builder()
                .reviewId(review.getId())
                .updatedAt(review.getUpdatedAt())
                .star(review.getStar())
                .build();
    }
}
