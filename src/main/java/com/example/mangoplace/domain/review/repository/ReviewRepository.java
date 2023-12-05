package com.example.mangoplace.domain.review.repository;


import com.example.mangoplace.domain.review.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

}
