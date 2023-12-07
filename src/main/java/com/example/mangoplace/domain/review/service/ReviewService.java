package com.example.mangoplace.domain.review.service;


import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.domain.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.review.exception.RestaurantIdNotFoundException;
import com.example.mangoplace.domain.review.exception.ReviewIdNotFoundException;
import com.example.mangoplace.domain.review.repository.ReviewRepository;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mangoplace.domain.review.exception.RestaurantIdNotFoundExceptionCode.RESTAURANT_ID_NOT_FOUND_EXCEPTION;
import static com.example.mangoplace.domain.review.exception.ReviewIdNotFoundExceptionCode.REVIEW_ID_NOT_FOUND_EXCEPTION;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public List<ReviewResponse> getShopReviews(String restaurantId) {
        Shop shop = shopRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new RestaurantIdNotFoundException(RESTAURANT_ID_NOT_FOUND_EXCEPTION));

        List<Review> reviews = reviewRepository.findByShop_RestaurantId(restaurantId);

        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }


    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest createReviewRequest) {
        Shop shop = shopRepository.findByRestaurantId(createReviewRequest.getRestaurantId())
                .orElseThrow(() -> new RestaurantIdNotFoundException(RESTAURANT_ID_NOT_FOUND_EXCEPTION));

        Review review = createReviewRequest.toEntity();
        review.setShop(shop);
        Review savedReview = reviewRepository.save(review);

        return CreateReviewResponse.fromEntity(savedReview);
    }

    @Transactional
    public UpdateReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewIdNotFoundException(REVIEW_ID_NOT_FOUND_EXCEPTION));

        review.update(updateReviewRequest);
        Review updatedReview = reviewRepository.save(review);

        return UpdateReviewResponse.fromEntity(updatedReview);
    }

    @Transactional
    public DeleteReviewResponse deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewIdNotFoundException(REVIEW_ID_NOT_FOUND_EXCEPTION));

        reviewRepository.delete(review);

        return DeleteReviewResponse.fromEntity(review);
    }
}
