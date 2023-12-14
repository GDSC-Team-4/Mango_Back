package com.example.mangoplace.domain.reviewimage.service;

import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.review.exception.ReviewIdNotFoundException;
import com.example.mangoplace.domain.review.repository.ReviewRepository;
import com.example.mangoplace.domain.reviewimage.dto.request.UploadImageRequest;
import com.example.mangoplace.domain.reviewimage.entity.ReviewImage;
import com.example.mangoplace.domain.reviewimage.repository.ReviewImageRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.example.mangoplace.domain.review.exception.ReviewIdNotFoundExceptionCode.REVIEW_ID_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor

public class ReviewImageService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;


    public void uploadImage(Long reviewId, UploadImageRequest uploadImageRequest) throws IOException {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewIdNotFoundException(REVIEW_ID_NOT_FOUND_EXCEPTION));

        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = uploadImageRequest.getImage().getContentType(); // 파일의 형식 ex) JPG

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                uploadImageRequest.getImage().getInputStream()
        );

        ReviewImage reviewImage = ReviewImage.builder()
                .imageUrl(generateImageUrl(blobInfo.getBlobId().getName()))
                .review(review)
                .build();
        reviewImageRepository.save(reviewImage);
    }

    private String generateImageUrl(String blobId) {
        return "https://storage.googleapis.com/" + bucketName + "/" + blobId;
    }
}
