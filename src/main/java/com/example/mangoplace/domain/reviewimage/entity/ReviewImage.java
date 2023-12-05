package com.example.mangoplace.domain.reviewimage.entity;

import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @JsonProperty("image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
