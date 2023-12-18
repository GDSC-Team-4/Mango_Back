package com.example.mangoplace.domain.scrap.service;

import com.example.mangoplace.domain.auth.repository.UserRepository;
import com.example.mangoplace.domain.auth.security.config.SecurityUtil;
import com.example.mangoplace.domain.scrap.dto.ScrapCreateResponse;
import com.example.mangoplace.domain.scrap.dto.UserScrapResponse;
import com.example.mangoplace.domain.scrap.entity.Scrap;
import com.example.mangoplace.domain.scrap.repository.ScrapRepository;
import com.example.mangoplace.domain.shop.entity.Shop;
import com.example.mangoplace.domain.shop.repository.ShopRepository;
import com.example.mangoplace.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public ScrapCreateResponse createScrap(String restaurantId) {

        Shop shop = shopRepository.findByRestaurantId(restaurantId).orElseThrow(
                RuntimeException::new
        );

        Long userId = securityUtil.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot find user"));

        Optional<Scrap> foundScrap = scrapRepository.findByShop_RestaurantIdAndUserId(restaurantId, userId);
        if (foundScrap.isPresent()) {
            throw new RuntimeException("이미 스크랩한 식당입니다");
        }

        Scrap scrap = new Scrap(shop, user);
//        Scrap scrap = new Scrap(shop);
        scrapRepository.save(scrap);
        return ScrapCreateResponse.builder()
                .id(scrap.getId())
                .restaurantId(scrap.getShop().getRestaurantId())
                .userId(scrap.getUser().getId())
                .build();
    }

    public void deleteScrap(String restaurantId) {
        shopRepository.findByRestaurantId(restaurantId).orElseThrow(
                RuntimeException::new
        );

        Long userId = securityUtil.getCurrentUserId();
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot find user"));


        Optional<Scrap> foundScrap = scrapRepository.findByShop_RestaurantIdAndUserId(restaurantId, userId);
//        Optional<Scrap> foundScrap = scrapRepository.findByShop_RestaurantId(restaurantId);
        if (foundScrap.isEmpty()) {
            throw new RuntimeException();
        }

        Long scrapId = foundScrap.get().getId();
        scrapRepository.deleteById(scrapId);
    }

    public List<UserScrapResponse> usersScrap() {
        Long userId = securityUtil.getCurrentUserId();
        List<Scrap> scraps = scrapRepository.findByUserId(userId);

        return scraps.stream()
                .map(scrap -> UserScrapResponse.builder()
                        .restaurantId(scrap.getShop().getRestaurantId())
                        .userId(scrap.getUser().getId())
                        .build())
                .collect(Collectors.toList());
    }
}
