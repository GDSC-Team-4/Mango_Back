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
public class DeleteReviewResponse {
    private Long reviewId;
    private LocalDateTime deleteDate;

    public static DeleteReviewResponse fromEntity(ReviewEntity review){
        return DeleteReviewResponse.builder()
                .reviewId(review.getId())
                .deleteDate(LocalDateTime.now())
                .build();
    }
}


