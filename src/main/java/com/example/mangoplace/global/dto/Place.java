package com.example.mangoplace.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Objects;

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

        // Extract image URL by parsing HTML
        String imageUrl = extractImageUrl(place.getPlaceUrl());
        place.setImageUrl(imageUrl);

        return place;
    }

    private void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Place 클래스의 extractImageUrl 메서드 부분
    private static String extractImageUrl(String placeUrl) {
        try {
            Document document = Jsoup.connect(placeUrl).get();
            System.out.println(document.html());

            // 추가: 이미지 엘리먼트를 안전하게 찾기 위한 선택자 변경
            Element imageElement = document.select("meta[property=og:image]").first();

            // 추가: null 체크 및 안전하게 이미지 URL 추출
            if (imageElement != null) {
                String imageUrl = imageElement.attr("content");
                return imageUrl;
            } else {
                // 이미지가 없을 경우 처리
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
