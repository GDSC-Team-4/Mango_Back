package com.example.mangoplace.global.service;

import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.example.mangoplace.global.dto.KakaoPlace;
import com.example.mangoplace.global.dto.Place;
import com.example.mangoplace.global.dto.PlaceResponseDto;
import com.example.mangoplace.global.repository.KakaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;
    private final ShopRepository shopRepository;
    private final ShopRepository shopRestaurantRepository;

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

        // Shop 엔터티 생성
        Shop shop = new Shop();
        shopRepository.save(shop);

        // 각 KakaoPlace의 ID를 추출하여 ShopRestaurant 엔터티에 저장
        List<Shop> shopRestaurants = kakaoPlaces.stream()
                .map(kakaoPlace -> new Shop(kakaoPlace.getId()))
                .collect(Collectors.toList());
        shopRestaurantRepository.saveAll(shopRestaurants);

        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(),PlaceResponseDto::from));
    }

    public PlaceResponseDto regionRestaurant(String region) {
        List<KakaoPlace> kakaoPlaces = kakaoRepository.findByKeyword(region);
        if (kakaoPlaces.isEmpty()) {
            throw new IllegalArgumentException("검색결과가 없습니다");
        }
        // Shop 엔터티 생성
        Shop shop = new Shop();
        shopRepository.save(shop);

        // 각 KakaoPlace의 ID를 추출하여 ShopRestaurant 엔터티에 저장
        List<Shop> shopRestaurants = kakaoPlaces.stream()
                .map(kakaoPlace -> new Shop(kakaoPlace.getId()))
                .collect(Collectors.toList());
        shopRestaurantRepository.saveAll(shopRestaurants);
        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(),PlaceResponseDto::from));
    }


}
