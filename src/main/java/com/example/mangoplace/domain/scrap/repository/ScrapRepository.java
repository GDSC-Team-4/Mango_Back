package com.example.mangoplace.domain.scrap.repository;

import com.example.mangoplace.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    Optional<Scrap> findByShop_RestaurantIdAndUserId(String restaurantId, Long userId);

    //후에 user 생기면 지워야함
    Optional<Scrap> findByShop_RestaurantId(String restaurantId);
}
