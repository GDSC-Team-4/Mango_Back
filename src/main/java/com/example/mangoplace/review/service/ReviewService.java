package com.example.mangoplace.review.service;

import com.example.mangoplace.review.dto.request.CreateReviewRequest;
import com.example.mangoplace.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.review.dto.response.CreateReviewResponse;
import com.example.mangoplace.review.dto.response.DeleteReviewResponse;
import com.example.mangoplace.review.dto.response.UpdateReviewResponse;
import com.example.mangoplace.review.dto.response.UserReviewListReponse;
import com.example.mangoplace.review.entity.ReviewEntity;
import com.example.mangoplace.review.repository.ReviewRepository;
import com.example.mangoplace.signup.entity.User;
import com.example.mangoplace.signup.repository.UserRepository;
import com.example.mangoplace.signup.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
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
        List<ReviewEntity> reviews = repository.findReviewsByUser(user);

        return reviews.stream()
                .map(UserReviewListReponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest createReviewRequest) throws Exception {
        String username = jwtUtils.getUsernameFromToken();
        ReviewEntity reviewEntity = createReviewRequest.toEntity();
        ReviewEntity savedReview = repository.save(reviewEntity);


        CreateReviewResponse createReviewResponse = CreateReviewResponse.builder()
                .reviewId(savedReview.getId())
                .createdAt(savedReview.getCreatedAt())
                .star(savedReview.getStar())
                .userId(savedReview.getUser().getId())
                .userName(savedReview.getUser().getUsername())
                .content(savedReview.getContent())
                .build();


        return createReviewResponse;
    }

    @Transactional
    public UpdateReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest){
        ReviewEntity review = repository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰id를 찾을수 없습니다"));
        review.update(updateReviewRequest);
        ReviewEntity updatedReview = repository.save(review);
        return UpdateReviewResponse.fromEntity(updatedReview);

    }

    @Transactional
    public DeleteReviewResponse deleteReview(Long reviewId) throws Exception {
        String username = jwtUtils.getUsernameFromToken();
        ReviewEntity review = repository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Id를 찾을수 없습니다"));
        repository.deleteById(reviewId);
        return DeleteReviewResponse.fromEntity(review);
    }

}
