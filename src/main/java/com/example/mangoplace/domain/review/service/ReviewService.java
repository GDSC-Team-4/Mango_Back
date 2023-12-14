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
import com.example.mangoplace.domain.reviewimage.entity.ReviewImage;
import com.example.mangoplace.domain.reviewimage.repository.ReviewImageRepository;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.mangoplace.domain.review.exception.RestaurantIdNotFoundExceptionCode.RESTAURANT_ID_NOT_FOUND_EXCEPTION;
import static com.example.mangoplace.domain.review.exception.ReviewIdNotFoundExceptionCode.REVIEW_ID_NOT_FOUND_EXCEPTION;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;
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
    public CreateReviewResponse createReviewWithImage(CreateReviewRequest request) throws IOException {
        Shop shop = shopRepository.findByRestaurantId(request.getRestaurantId())
                .orElseThrow(() -> new RestaurantIdNotFoundException(RESTAURANT_ID_NOT_FOUND_EXCEPTION));

        // 리뷰 엔터티 생성
        Review review = request.toEntity();
        review.setShop(shop);
        Review savedReview = reviewRepository.save(review);

        // 이미지 업로드
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = request.getImage().getContentType(); // 파일의 형식 ex) JPG

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                request.getImage().getInputStream()
        );

        // 리뷰 이미지 엔터티 생성 및 저장
        ReviewImage reviewImage = ReviewImage.builder()
                .imageUrl(generateImageUrl(blobInfo.getBlobId().getName()))
                .review(savedReview)
                .build();
        reviewImageRepository.save(reviewImage);

        return CreateReviewResponse.fromEntity(savedReview);
    }
    private String generateImageUrl(String blobId) {
        return "https://storage.googleapis.com/" + bucketName + "/" + blobId;
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
