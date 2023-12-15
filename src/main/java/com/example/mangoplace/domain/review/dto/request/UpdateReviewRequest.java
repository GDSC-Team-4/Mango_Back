package com.example.mangoplace.domain.review.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewRequest {
    private String restaurantId;
    private Double star;
    private String content;
}
