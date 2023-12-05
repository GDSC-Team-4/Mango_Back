package com.example.mangoplace.domain.review.dto.response;


import com.example.mangoplace.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteReviewResponse {
    private Long reviewId;
    private LocalDateTime deleteDate;

    public static DeleteReviewResponse fromEntity(Review review){
        return DeleteReviewResponse.builder()
                .reviewId(review.getId())
                .deleteDate(LocalDateTime.now())
                .build();
    }
}


