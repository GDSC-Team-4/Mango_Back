package com.example.mangoplace.domain.scrap.service;

import com.example.mangoplace.domain.scrap.dto.ScrapCreateResponse;
import com.example.mangoplace.domain.scrap.entity.Scrap;
import com.example.mangoplace.domain.scrap.repository.ScrapRepository;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.example.mangoplace.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final ShopRepository shopRepository;

    public ScrapCreateResponse createScrap(String restaurantId) {

        Shop shop = shopRepository.findByRestaurantId(restaurantId).orElseThrow(
                RuntimeException::new
        );

        //TODO: 토큰으로 유저 id 받아오기
        Long userId=1L;
        User user = new User("test", "test@mail", "123456");
        //TODO: 유저 유효성 검사

        Optional<Scrap> foundScrap = scrapRepository.findByShop_RestaurantIdAndUserId(restaurantId, userId);
        if(foundScrap.isPresent()){
            throw new RuntimeException();
        }

        //TODO: 후에 user 생기면 아래거 쓰기
//        Scrap scrap = new Scrap(shop, user);
        Scrap scrap = new Scrap(shop);
        scrapRepository.save(scrap);
        return ScrapCreateResponse.builder()
                .id(scrap.getId())
                .restaurantId(scrap.getShop().getRestaurantId())
                .userId(1L)
                .build();
    }

    public void deleteScrap(String restaurantId) {
        Shop shop = shopRepository.findByRestaurantId(restaurantId).orElseThrow(
                RuntimeException::new
        );

        //TODO: 토큰으로 유저 id 받아오기
        Long userId=1L;
        User user = new User("test", "test@mail", "123456");
        //TODO: 유저 유효성 검사

        //TODO: 후에 user 생기면 아래거 쓰기
//        Optional<Scrap> foundScrap = scrapRepository.findByShop_RestaurantIdAndUserId(restaurantId, userId);
        Optional<Scrap> foundScrap = scrapRepository.findByShop_RestaurantId(restaurantId);
        if(foundScrap.isEmpty()){
            throw new RuntimeException();
        }

        Long scrapId = foundScrap.get().getId();
        scrapRepository.deleteById(scrapId);
    }

    //TODO: 유저 생기면 해당 유저가 스크랩 한 shop들 가져다 주기
}
