package com.example.mangoplace.global.repository;

import com.example.mangoplace.global.dto.KakaoPlace;
import com.example.mangoplace.global.dto.KakaoResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class KakaoRepository {

    public static final String REST_API_KEY = "KakaoAK 4116be3b3256a41225d034f5bab9685f";
    private final RestTemplate restTemplate;
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/keyword.JSON?category_group_code=FD6";
    private static final String KAKAO_CATEGORY_GROUP_CODE = "category_group_code=FD6";
    public List<KakaoPlace> findByKeyword(String keyword) {
        URI uri = UriComponentsBuilder.fromUriString(KAKAO_API_URL)
                .queryParam("query", keyword)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", REST_API_KEY);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

        RequestEntity<String> request = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        log.info(String.valueOf(restTemplate.exchange(request, KakaoResponseDto.class).getBody()));

        return restTemplate.exchange(request, KakaoResponseDto.class).getBody().getKakaoPlaces();
    }
}
