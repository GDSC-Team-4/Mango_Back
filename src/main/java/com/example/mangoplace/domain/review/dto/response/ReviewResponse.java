package com.example.mangoplace.domain.review.dto.response;

import com.example.mangoplace.domain.review.entity.ReviewEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private long id;
    private String username;
    private String content;
    private int star;
    private LocalDateTime reviewDate;
    private Long userId;

    public static ReviewResponse fromEntity(ReviewEntity review){
        return ReviewResponse.builder()
                .content(review.getContent())
                .star(review.getStar())
                .reviewDate(review.getCreatedAt())
                .id(review.getId())
                .userId(review.getUser().getId())
                .username(review.getUser().getUsername())
                .build();
    }
}
