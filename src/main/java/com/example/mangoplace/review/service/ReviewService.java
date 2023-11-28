package com.example.mangoplace.review.service;

import com.example.mangoplace.review.dto.ReviewDTO;
import com.example.mangoplace.review.entity.ReviewEntity;
import com.example.mangoplace.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    public List<ReviewDTO> getAllReviews() {
        List<ReviewEntity> reviewEntities = (List<ReviewEntity>) repository.findAll();
        return reviewEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Long id) {
        Optional<ReviewEntity> optionalReview = repository.findById(id);
        return optionalReview.map(this::convertToDTO).orElse(null);
    }

    public void createReview(ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = convertToEntity(reviewDTO);
        repository.save(reviewEntity);
    }

    public void updateReview(Long id, ReviewDTO reviewDTO) {
        Optional<ReviewEntity> optionalReview = repository.findById(id);
        optionalReview.ifPresent(existingReview -> {
            existingReview.setContent(reviewDTO.getContent());
            existingReview.setStar(reviewDTO.getStar());
            existingReview.setUpdatedAt(LocalDateTime.now());
            repository.save(existingReview);
        });
    }

    public void deleteReview(Long id) {
        repository.deleteById(id);
    }

    private ReviewDTO convertToDTO(ReviewEntity reviewEntity) {
        return new ReviewDTO(
                reviewEntity.getId(),
                reviewEntity.getContent(),
                reviewEntity.getStar(),
                reviewEntity.getCreatedAt(),
                reviewEntity.getUpdatedAt()
        );
    }

    private ReviewEntity convertToEntity(ReviewDTO reviewDTO) {
        return new ReviewEntity(
                reviewDTO.getId(),
                reviewDTO.getContent(),
                reviewDTO.getStar(),
                reviewDTO.getCreatedAt(),
                reviewDTO.getUpdatedAt()
        );
    }
}
