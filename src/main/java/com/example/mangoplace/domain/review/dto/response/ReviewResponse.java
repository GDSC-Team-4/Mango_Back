package com.example.mangoplace.domain.review.dto.response;

import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.reviewimage.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> imageUrls;

    public static ReviewResponse fromEntity(Review review) {
        List<String> imageUrls = review.getReviewImages().stream()
                .map(ReviewImage::getImageUrl)
                .collect(Collectors.toList());

        return ReviewResponse.builder()
                .content(review.getContent())
                .star(review.getStar())
                .updatedDate(review.getUpdatedAt())
                .createdDate(review.getCreatedAt())
                .id(review.getId())
                .restaurantId(review.getShop().getRestaurantId())
                .imageUrls(imageUrls)
                .build();
    }
}
