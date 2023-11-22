package com.example.mangoplace.controller;

import com.example.mangoplace.dto.ErrorResponse;
import com.example.mangoplace.dto.PlaceResponseDto;
import com.example.mangoplace.service.KakaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    /**
     * 프론트엔드가 키워드를 검색 하였을때
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<PlaceResponseDto> find(@RequestParam(required = false) String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException("검색어가 없습니다");
        }
        return ResponseEntity.ok(kakaoService.search(keyword));
    }

    @GetMapping("/main")
    public ResponseEntity<List<PlaceResponseDto>> findRegionRestaurant() {
        return ResponseEntity.ok(kakaoService.regionRestaurant());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("오류발생오류발생 삐용삐용", e.getMessage()));
    }
}
