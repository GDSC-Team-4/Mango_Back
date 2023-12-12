package com.example.mangoplace.domain.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapCreateResponse {
    private Long id;
    private String restaurantId;
    private Long userId;
}
