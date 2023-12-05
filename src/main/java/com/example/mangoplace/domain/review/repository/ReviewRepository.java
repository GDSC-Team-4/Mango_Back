package com.example.mangoplace.domain.review.repository;


import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findReviewsByUser(Optional<User> user);
}
