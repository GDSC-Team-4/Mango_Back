package com.example.mangoplace.service;

import com.example.mangoplace.dto.KakaoPlace;
import com.example.mangoplace.dto.Place;
import com.example.mangoplace.dto.PlaceResponseDto;
import com.example.mangoplace.repository.KakaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;

    /**
     * kakaoplace -> place 작업을 여기서
     * kakaoPlace = 카카오 장소 검색 결과 값
     * place = 프론트한테 넘겨줄 결과 값
     */
    public PlaceResponseDto search(String keyword) {
        List<KakaoPlace> kakaoPlaces = kakaoRepository.findByKeyword(keyword);
        if (kakaoPlaces.isEmpty()) {
            throw new IllegalArgumentException("검색결과가 없습니다");
        }
        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(),PlaceResponseDto::from));
    }

    public PlaceResponseDto regionRestaurant(String region) {
        List<KakaoPlace> kakaoPlaces = kakaoRepository.findByKeyword(region);
        if (kakaoPlaces.isEmpty()) {
            throw new IllegalArgumentException("검색결과가 없습니다");
        }
        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(),PlaceResponseDto::from));
    }
}
