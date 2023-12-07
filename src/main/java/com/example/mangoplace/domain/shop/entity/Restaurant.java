package com.example.mangoplace.domain.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Restaurant(String restaurantId, Shop shop) {
        this.restaurantId = restaurantId;
        this.shop = shop;
    }
}
