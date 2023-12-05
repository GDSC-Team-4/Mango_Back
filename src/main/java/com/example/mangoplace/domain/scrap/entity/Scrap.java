package com.example.mangoplace.domain.scrap.entity;
import com.example.mangoplace.signup.entity.User;
import com.example.mangoplace.domain.shop.entity.Shop;
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
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="user_id")
    private  User user;

}
