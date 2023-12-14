package com.example.mangoplace.domain.reviewimage.repository;

import com.example.mangoplace.domain.reviewimage.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
