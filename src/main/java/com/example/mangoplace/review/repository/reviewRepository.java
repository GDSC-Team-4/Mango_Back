package com.example.mangoplace.review.repository;

import com.example.mangoplace.review.entity.reviewEntity;
import org.springframework.data.repository.CrudRepository;

public interface reviewRepository extends CrudRepository<reviewEntity, Long> {
}
