package com.example.mangoplace.global.service;

import com.example.mangoplace.domain.review.dto.response.ReviewResponse;
import com.example.mangoplace.domain.review.entity.Review;
import com.example.mangoplace.domain.review.repository.ReviewRepository;
import com.example.mangoplace.domain.scrap.repository.ScrapRepository;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.example.mangoplace.global.dto.KakaoPlace;
import com.example.mangoplace.global.dto.Place;
import com.example.mangoplace.global.dto.PlaceDetailResponseDto;
import com.example.mangoplace.global.dto.PlaceResponseDto;
import com.example.mangoplace.global.repository.KakaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;
    private final ShopRepository shopRepository;
    private final ReviewRepository reviewRepository;
    private final ScrapRepository scrapRepository;

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
            if (!shopRepository.existsShopByRestaurantId(kakaoPlace.getId())) {
                Shop shop = Shop.builder()
                        .restaurantId(kakaoPlace.getId())
                        .build();
                shopRepository.save(shop);
            }
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
            for (KakaoPlace kakaoPlace : kakaoPlaces) {
                if (!shopRepository.existsShopByRestaurantId(kakaoPlace.getId())) {
                    Shop shop = Shop.builder()
                            .restaurantId(kakaoPlace.getId())
                            .build();
                    shopRepository.save(shop);
                }
            }
            PlaceResponseDto placeResponseDto = kakaoPlaces.stream()
                    .map(Place::from)
                    .collect(collectingAndThen(toList(), PlaceResponseDto::from));
            placeResponseDtoList.add(placeResponseDto);
        }

        return placeResponseDtoList;
    }

    public PlaceDetailResponseDto placeDetail(String shopId) {
        Shop shop = shopRepository.findByRestaurantId(shopId).orElseThrow(IllegalArgumentException::new);
        List<Review> reviews = reviewRepository.findByShop_RestaurantId(shopId);

        String restaurantId = shop.getRestaurantId(); //shopId==restaurantId
        Long scrapCounts = scrapRepository.countByShop_RestaurantId(restaurantId);

        return PlaceDetailResponseDto.builder()
                .reviews(reviews.stream().map(ReviewResponse::fromEntity).collect(Collectors.toList()))
                .scrapCount(scrapCounts)
                .build();
    }

}
