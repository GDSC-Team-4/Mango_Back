package com.example.mangoplace.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

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

    public static Place from(KakaoPlace kakaoPlace) {
        return new Place(
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
                kakaoPlace.getPlaceUrl()
        );
    }
}
