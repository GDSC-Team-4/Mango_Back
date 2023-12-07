package com.example.mangoplace.domain.shop.repository;

import com.example.mangoplace.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    Optional<Shop> findByRestaurantId(String restaurantId);
}
