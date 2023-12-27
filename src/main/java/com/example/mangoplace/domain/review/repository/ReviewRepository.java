package com.example.mangoplace.domain.review.repository;


import com.example.mangoplace.domain.auth.entity.User;
import com.example.mangoplace.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByShop_RestaurantId(String restaurantId);
    List<Review> findReviewsByUser(User user);

}
