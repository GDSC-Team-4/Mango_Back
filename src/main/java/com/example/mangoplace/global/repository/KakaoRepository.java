package com.example.mangoplace.global.repository;

import com.example.mangoplace.global.dto.KakaoPlace;
import com.example.mangoplace.global.dto.KakaoResponseDto;
import com.example.mangoplace.global.dto.Document;
import com.example.mangoplace.global.dto.ImageResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class KakaoRepository {

    private Environment environment;
    private final RestTemplate restTemplate;

    public List<KakaoPlace> findByKeyword(String keyword) {


        List<KakaoPlace> places = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            UriComponentsBuilder placeBuilder = UriComponentsBuilder.fromUriString(environment.getProperty("kakao.place-url"))
                .encode(StandardCharsets.UTF_8)
                .queryParam("query", keyword);
            URI uri = placeBuilder.queryParam("page", i).build().toUri();
            log.info(String.valueOf(uri));
            RequestEntity<String> request = new RequestEntity<>(getHttpHeaders(), HttpMethod.GET, uri);
            List<KakaoPlace> kakaoPlaces = restTemplate.exchange(request, KakaoResponseDto.class).getBody().getKakaoPlaces();
            for (KakaoPlace kakaoPlace : kakaoPlaces) {
                String region = kakaoPlace.getAddressName().split(" ")[1];
                String placeName = region + kakaoPlace.getPlaceName();
                String imageUrl = findImageUrlByPlaceName(placeName);
                kakaoPlace.setImageUrl(imageUrl);
            }
            places.addAll(kakaoPlaces);
        }
        return places;
    }

    private String findImageUrlByPlaceName(String placeName) {

        URI imageUri = UriComponentsBuilder.fromUriString(environment.getProperty("kakao.image-url"))
                .encode(StandardCharsets.UTF_8)
                .queryParam("query", placeName)
                .build()
                .toUri();
        RequestEntity<String> imageRequest = new RequestEntity<>(getHttpHeaders(), HttpMethod.GET, imageUri);
        List<Document> documents = restTemplate.exchange(imageRequest, ImageResponseDto.class).getBody().getDocuments();
        if (!documents.isEmpty()) {
            return documents.get(0).getImageUrl();
        }
        else
            return "https://storage.googleapis.com/grape-plate/GrapePlate.png";
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", environment.getProperty("kakao.rest-api-key"));
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        return httpHeaders;
    }
}
