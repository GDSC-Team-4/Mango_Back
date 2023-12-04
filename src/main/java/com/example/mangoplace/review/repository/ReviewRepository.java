package com.example.mangoplace.review.repository;

import com.example.mangoplace.review.entity.ReviewEntity;
import com.example.mangoplace.signup.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {
    List<ReviewEntity> findReviewsByUser(Optional<User> user);
}
