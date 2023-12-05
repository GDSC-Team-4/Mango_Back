package com.example.mangoplace.domain.review.entity;

import com.example.mangoplace.domain.reviewimage.entity.ReviewImage;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.review.dto.request.UpdateReviewRequest;
import com.example.mangoplace.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;

    private Double star;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="id")
    private Shop shop;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    public void update(UpdateReviewRequest reviewUpdateRequest) {
        this.star = reviewUpdateRequest.getStar();
        this.content = reviewUpdateRequest.getContent();
    }

}

