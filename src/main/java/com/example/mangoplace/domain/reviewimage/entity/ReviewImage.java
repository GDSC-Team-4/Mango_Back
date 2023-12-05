package com.example.mangoplace.domain.reviewimage.entity;

import com.example.mangoplace.domain.review.entity.ReviewEntity;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @JsonProperty("image_url")
    private String imageUrl;



}
