package com.example.mangoplace.global.dto;

import com.example.mangoplace.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDetailResponseDto {

    private Long scrapCount;
    private List<Review> reviews;
}
