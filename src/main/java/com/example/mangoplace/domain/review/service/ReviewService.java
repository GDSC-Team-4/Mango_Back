package com.example.mangoplace.domain.review.service;


import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.domain.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.review.exception.OnlyImageCanUploadedException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.mangoplace.domain.review.exception.OnlyImageCanUploadedExceptionCode.ONLY_IMAGE_CAN_UPLOADED_EXCEPTION;
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
    public CreateReviewResponse createReviewWithImages(CreateReviewRequest request) throws IOException {
        Shop shop = shopRepository.findByRestaurantId(request.getRestaurantId())
                .orElseThrow(() -> new RestaurantIdNotFoundException(RESTAURANT_ID_NOT_FOUND_EXCEPTION));

        // 리뷰 엔터티 생성
        Review review = request.toEntity();
        review.setShop(shop);
        Review savedReview = reviewRepository.save(review);

        // 이미지 업로드 및 연결
        List<MultipartFile> images = request.getImages();
        if (images != null) {
            for (MultipartFile image : images) {
                String originalName = image.getOriginalFilename();
                String ext = image.getContentType();
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String fileName = uuid + "_" + originalName;

                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder(bucketName, fileName)
                                .setContentType(ext)
                                .build(),
                        image.getInputStream()
                );

                ReviewImage reviewImage = ReviewImage.builder()
                        .imageUrl(generateImageUrl(blobInfo.getBlobId().getName()))
                        .review(savedReview)
                        .build();
                reviewImageRepository.save(reviewImage);
            }
        }

        return CreateReviewResponse.fromEntity(savedReview);
    }


    private String generateImageUrl(String blobId) {
        return "https://storage.googleapis.com/" + bucketName + "/" + blobId;
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf('.') != -1) {
            return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        }
        return null;
    }

    private boolean isValidImageExtension(String ext) {
        // 허용된 확장자 목록
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");

        return ext != null && allowedExtensions.contains(ext);
    }

    @Transactional
    public UpdateReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest, List<MultipartFile> images) throws IOException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewIdNotFoundException(REVIEW_ID_NOT_FOUND_EXCEPTION));

        // 이미지 확장자 체크
        if (images != null) {
            for (MultipartFile image : images) {
                String originalName = image.getOriginalFilename();
                String ext = getFileExtension(originalName);

                // 이미지 확장자가 허용된 확장자인지 확인
                if (!isValidImageExtension(ext)) {
                    throw new OnlyImageCanUploadedException(ONLY_IMAGE_CAN_UPLOADED_EXCEPTION);
                }
            }

            // 이미지 업로드 및 연결
            for (MultipartFile image : images) {
                String originalName = image.getOriginalFilename();
                String ext = getFileExtension(originalName);

                String uuid = UUID.randomUUID().toString().replace("-", "");
                String fileName = uuid + "_" + originalName;

                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder(bucketName, fileName)
                                .setContentType(ext)
                                .build(),
                        image.getInputStream()
                );

                ReviewImage reviewImage = ReviewImage.builder()
                        .imageUrl(generateImageUrl(blobInfo.getBlobId().getName()))
                        .review(review)
                        .build();
                reviewImageRepository.save(reviewImage);
            }
        }

        // 리뷰 업데이트
        review.update(updateReviewRequest);

        Review updatedReview = reviewRepository.save(review);

        return UpdateReviewResponse.fromEntity(updatedReview);
    }



    @Transactional
    public DeleteReviewResponse deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewIdNotFoundException(REVIEW_ID_NOT_FOUND_EXCEPTION));

        // 연관된 이미지를 GCS에서 삭제
        for (ReviewImage image : review.getReviewImages()) {
            deleteImage(image.getImageUrl());
        }

        // 리뷰를 데이터베이스에서 삭제
        reviewRepository.delete(review);

        return DeleteReviewResponse.fromEntity(review);
    }

    private void deleteImage(String imageUrl) {
        // imageUrl에서 Blob ID 추출
        String blobId = extractBlobIdFromImageUrl(imageUrl);

        // GCS에서 이미지 삭제
        storage.delete(bucketName, blobId);
    }

    private String extractBlobIdFromImageUrl(String imageUrl) {
        // URL에서 마지막 "/"의 인덱스를 찾기
        int lastSlashIndex = imageUrl.lastIndexOf('/');

        // 마지막 "/" 이후의 문자열을 추출 (Blob ID)
        if (lastSlashIndex != -1 && lastSlashIndex < imageUrl.length() - 1) {
            return imageUrl.substring(lastSlashIndex + 1);
        } else {
            // "/"이 발견되지 않거나 마지막 문자가 "/"인 경우
            throw new IllegalArgumentException("Invalid image URL format");
        }
    }

}
