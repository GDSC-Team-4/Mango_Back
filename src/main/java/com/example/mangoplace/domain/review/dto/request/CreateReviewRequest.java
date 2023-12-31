package com.example.mangoplace.domain.review.dto.request;


import com.example.mangoplace.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private String restaurantId;
    private Double star;
    private String content;
    private List<MultipartFile> images;

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .star(star)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
