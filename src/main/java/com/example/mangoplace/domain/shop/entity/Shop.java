package com.example.mangoplace.domain.shop.entity;

import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.scrap.entity.Scrap;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "restaurant_id")})
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @JsonProperty("view_count")
    @Builder.Default
    private Integer viewCount = 0;

    @JsonProperty("restaurant_id")
    @Column(name = "restaurant_id")
    private String restaurantId;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();


    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

}