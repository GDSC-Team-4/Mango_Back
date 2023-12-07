package com.example.mangoplace.global.service;

import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.example.mangoplace.global.dto.KakaoPlace;
import com.example.mangoplace.global.dto.Place;
import com.example.mangoplace.global.dto.PlaceResponseDto;
import com.example.mangoplace.global.repository.KakaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;
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

        for (KakaoPlace kakaoPlace : kakaoPlaces) {
            Shop shop = Shop.builder()
                    .restaurantId(kakaoPlace.getId())
                    .build();
            shopRepository.save(shop);
        }

        return kakaoPlaces.stream()
                .map(Place::from)
                .collect(collectingAndThen(toList(), PlaceResponseDto::from));
    }


    public List<PlaceResponseDto> regionRestaurant() {

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();

        List<String> placeList = List.of(new String[]{"강남역", "홍대입구역", "역곡역"});

        for (String place : placeList) {
            List<KakaoPlace> kakaoPlaces = kakaoRepository.findByKeyword(place);

            if (kakaoPlaces.isEmpty()) {
                throw new IllegalArgumentException("검색결과가 없습니다");
            }
            PlaceResponseDto placeResponseDto = kakaoPlaces.stream()
                    .map(Place::from)
                    .collect(collectingAndThen(toList(), PlaceResponseDto::from));
            placeResponseDtoList.add(placeResponseDto);
        }

        return placeResponseDtoList;
    }

}
