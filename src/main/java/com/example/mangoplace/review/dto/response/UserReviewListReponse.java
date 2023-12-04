package com.example.mangoplace.review.dto.response;

import com.example.mangoplace.review.entity.ReviewEntity;
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
    private Long userId;
    private String userName;
    private String content;
    private Integer star;

    public static UserReviewListReponse fromEntity(ReviewEntity review){
        return UserReviewListReponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .userId(review.getUser().getId())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
