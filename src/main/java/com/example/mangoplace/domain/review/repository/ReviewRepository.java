package com.example.mangoplace.domain.review.repository;


import com.example.mangoplace.domain.review.entity.ReviewEntity;
import com.example.mangoplace.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {
    List<ReviewEntity> findReviewsByUser(Optional<User> user);
}
