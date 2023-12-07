package com.example.mangoplace.domain.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewRequest {
    private String restaurantId;
    private Double star;
    private String content;
}
