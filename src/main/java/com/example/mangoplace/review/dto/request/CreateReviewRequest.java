package com.example.mangoplace.review.dto.request;

import com.example.mangoplace.review.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private Integer star;
    private String content;

    public ReviewEntity toEntity(){
        return ReviewEntity.builder()
                .content(content)
                .star(star)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
