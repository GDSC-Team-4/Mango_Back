package com.example.mangoplace.domain.review.service;


import com.example.mangoplace.domain.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.domain.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.domain.review.dto.response.UserReviewListReponse;
import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.review.repository.ReviewRepository;
import com.example.mangoplace.domain.user.entity.User;
import com.example.mangoplace.domain.user.repository.UserRepository;
import com.example.mangoplace.domain.user.security.JwtUtils;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;


    @Transactional
    public List<UserReviewListReponse> getUserReviews() throws Exception {
        String username = jwtUtils.getUsernameFromToken();
        Optional<User> user = userRepository.findByUsername(username);
        List<Review> reviews = repository.findReviewsByUser(user);

        return reviews.stream()
                .map(UserReviewListReponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest createReviewRequest) throws Exception {
        Long userId = JwtUtils.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("userId를 찾을수 없습니다"));
        Review review = createReviewRequest.toEntity();
        Review savedReview = repository.save(review);


        CreateReviewResponse createReviewResponse = CreateReviewResponse.builder()
                .reviewId(savedReview.getId())
                .createdAt(savedReview.getCreatedAt())
                .star(savedReview.getStar())
                .userId(userId)
                .content(savedReview.getContent())
                .build();

        return createReviewResponse;
    }

    @Transactional
    public UpdateReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest){
        Review review = repository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰id를 찾을수 없습니다"));
        review.update(updateReviewRequest);
        Review updatedReview = repository.save(review);
        return UpdateReviewResponse.fromEntity(updatedReview);

    }

    @Transactional
    public DeleteReviewResponse deleteReview(Long reviewId) throws Exception {
        String username = jwtUtils.getUsernameFromToken();
        Review review = repository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Id를 찾을수 없습니다"));
        repository.deleteById(reviewId);
        return DeleteReviewResponse.fromEntity(review);
    }

}
