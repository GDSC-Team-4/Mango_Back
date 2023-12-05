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
public class UserReviewListReponse {
    private Long reviewId;
    private LocalDateTime createdAt;
    private String content;
    private Double star;

    public static UserReviewListReponse fromEntity(Review review){
        return UserReviewListReponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
