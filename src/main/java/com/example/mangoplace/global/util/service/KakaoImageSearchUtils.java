package com.example.mangoplace.global.util.service;


import com.example.mangoplace.global.dto.KakaoResponseDto;
import com.example.mangoplace.global.util.dto.Document;
import com.example.mangoplace.global.util.dto.ImageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Slf4j
public class KakaoImageSearchUtils {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static final String REST_API_KEY = "KakaoAK 4116be3b3256a41225d034f5bab9685f";

    private static String URL = "https://dapi.kakao.com/v2/search/image";
    public static List<Document> searchImage(String query) {
        URI uri = UriComponentsBuilder.fromUriString(URL)
                .queryParam("query", query)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", REST_API_KEY);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

        RequestEntity<String> request = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        log.info(String.valueOf(restTemplate.exchange(request, KakaoResponseDto.class).getBody()));

        return restTemplate.exchange(request, ImageResponseDto.class).getBody().getDocuments();
    }
}
