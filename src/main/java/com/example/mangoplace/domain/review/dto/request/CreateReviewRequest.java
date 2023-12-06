package com.example.mangoplace.domain.review.dto.request;


import com.example.mangoplace.domain.review.entity.Review;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private String restaurantId;
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
