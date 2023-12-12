package com.example.mangoplace.domain.scrap.entity;

import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Scrap(Shop shop, User user) {
        this.shop = shop;
        this.user = user;
    }

    //후에 user 생기면 지워야함
    public Scrap(Shop shop) {
        this.shop = shop;
    }
}
