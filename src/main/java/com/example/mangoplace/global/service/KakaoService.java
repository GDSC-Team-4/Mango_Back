package com.example.mangoplace.global.service;

import com.example.mangoplace.domain.shop.entity.Restaurant;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.RestaurantRepository;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.example.mangoplace.global.dto.KakaoPlace;
import com.example.mangoplace.global.dto.Place;
import com.example.mangoplace.global.dto.PlaceResponseDto;
import com.example.mangoplace.global.repository.KakaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantRepository shopRestaurantRepository;
    private final ShopRepository shopRepository;

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

        // 각 KakaoPlace의 ID를 추출하여 Restaurant 엔터티에 저장
        List<Restaurant> restaurants = new ArrayList<>();
        for (KakaoPlace kakaoPlace : kakaoPlaces) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(kakaoPlace.getId());
            restaurant.setShop(shop);
            restaurantRepository.save(restaurant);
            restaurants.add(restaurant);
        }

        shop.setRestaurants(restaurants); // Shop 엔터티에 레스토랑 목록 설정

        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(), PlaceResponseDto::from));
    }


    public PlaceResponseDto regionRestaurant(String region) {
        List<KakaoPlace> kakaoPlaces = kakaoRepository.findByKeyword(region);
        if (kakaoPlaces.isEmpty()) {
            throw new IllegalArgumentException("검색결과가 없습니다");
        }
        // Shop 엔터티 생성
        Shop shop = new Shop();
        shopRepository.save(shop);

        List<Restaurant> restaurants = kakaoPlaces.stream()
                .map(kakaoPlace -> new Restaurant(kakaoPlace.getId(), shop))
                .collect(Collectors.toList());
        restaurantRepository.saveAll(restaurants);

        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(),PlaceResponseDto::from));
    }


}
