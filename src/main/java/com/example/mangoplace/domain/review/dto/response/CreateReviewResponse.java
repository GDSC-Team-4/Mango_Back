package com.example.mangoplace.domain.review.dto.response;


import com.example.mangoplace.domain.auth.entity.User;
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
public class CreateReviewResponse {
    private Long reviewId;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private String content;
    private Double star;
    private String restaurantId;

    public static CreateReviewResponse fromEntity(Review review, User user){
        return CreateReviewResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .star(review.getStar())
                .userId(user.getId())
                .userName(user.getUsername())
                .restaurantId(review.getShop().getRestaurantId())
                .build();
    }
}

