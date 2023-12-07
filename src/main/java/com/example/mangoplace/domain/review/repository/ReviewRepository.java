package com.example.mangoplace.domain.review.repository;


import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByShop_RestaurantId(String restaurantId);
}
