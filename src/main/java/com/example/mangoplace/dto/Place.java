package com.example.mangoplace.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프론트한테 줄 객체
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Place {
    private String id;
    private String placeName;
    private String roadAddressName;
    private String placeUrl;

    public static Place from(KakaoPlace kakaoPlace) {
        return new Place(kakaoPlace.getId(),
                kakaoPlace.getPlaceName(),
                kakaoPlace.getRoadAddressName(),
                kakaoPlace.getPlaceUrl());
    }
}
