package com.example.mangoplace.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private long id;
    private String content;
    private int star;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
