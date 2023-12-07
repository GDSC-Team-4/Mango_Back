package com.example.mangoplace.domain.review.dto.response;


import com.example.mangoplace.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private long id;
    private String username;
    private String content;
    private Double star;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long userId;
    private String restaurantId;

    public static ReviewResponse fromEntity(Review review) {
        return ReviewResponse.builder()
                .content(review.getContent())
                .star(review.getStar())
                .updatedDate(review.getUpdatedAt())
                .createdDate(review.getCreatedAt())
                .id(review.getId())
                .build();
    }

}
