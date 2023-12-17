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
    private Long id;
    private String username;
    private String content;
    private Double star;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long userId;
    private String restaurantId;
    private List<String> imageUrls;

    public static ReviewResponse fromEntity(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }

        List<String> imageUrls = review.getReviewImages().stream()
                .map(ReviewImage::getImageUrl)
                .collect(Collectors.toList());

        Long userId = null;
        String username = null;

        // Review에 User가 연결되어 있는지 확인
        if (review.getUser() != null) {
            userId = review.getUser().getId();
            username = review.getUser().getUsername();
        }

        String restaurantId = null;

        // Review에 Shop이 연결되어 있는지 확인
        if (review.getShop() != null) {
            restaurantId = review.getShop().getRestaurantId();
        }

        return ReviewResponse.builder()
                .userId(userId)
                .username(username)
                .content(review.getContent())
                .star(review.getStar())
                .updatedDate(review.getUpdatedAt())
                .createdDate(review.getCreatedAt())
                .id(review.getId())
                .restaurantId(restaurantId)
                .imageUrls(imageUrls)
                .build();
    }
}
