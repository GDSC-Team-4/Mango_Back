package com.example.mangoplace.global.dto;

import com.example.mangoplace.global.util.dto.Document;
import com.example.mangoplace.global.util.service.KakaoImageSearchUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 프론트한테 줄 객체
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Place {
    private String id;
    private String placeName;
    private String categoryName;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;
    private String y;
    private String placeUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public static Place from(KakaoPlace kakaoPlace) {
        Place place = new Place(
                kakaoPlace.getId(),
                kakaoPlace.getPlaceName(),
                kakaoPlace.getCategoryName(),
                kakaoPlace.getCategoryGroupCode(),
                kakaoPlace.getCategoryGroupName(),
                kakaoPlace.getPhone(),
                kakaoPlace.getAddressName(),
                kakaoPlace.getRoadAddressName(),
                kakaoPlace.getX(),
                kakaoPlace.getY(),
                kakaoPlace.getPlaceUrl(),
                null // Set imageUrl to null initially
        );

        // Use KakaoImageSearchUtils to search for images
        List<Document> imageDocuments = KakaoImageSearchUtils.searchImage(place.getPlaceName());

        // Check if imageDocuments list is not empty and set the first image URL
        if (!imageDocuments.isEmpty()) {
            place.setImageUrl(imageDocuments.get(0).getImageUrl());
        }

        return place;
    }
}
