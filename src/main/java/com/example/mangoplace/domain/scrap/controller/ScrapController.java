package com.example.mangoplace.domain.scrap.controller;

import com.example.mangoplace.domain.scrap.dto.ScrapCreateResponse;
import com.example.mangoplace.domain.scrap.service.ScrapService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

   @PostMapping("/shop/{restaurantId}/scrap")
    public ResponseEntity<ScrapCreateResponse> createScrap(@PathVariable String restaurantId) {
        return ResponseEntity.ok(scrapService.createScrap(restaurantId));
    }

    @DeleteMapping("/shop/{restaurantId}/scrap")
    public ResponseEntity<String> deleteScrap(@PathVariable String restaurantId) {
        scrapService.deleteScrap(restaurantId);
        return ResponseEntity.ok(restaurantId+" 식당의 스크랩이 삭제되었습니다");
    }
}
