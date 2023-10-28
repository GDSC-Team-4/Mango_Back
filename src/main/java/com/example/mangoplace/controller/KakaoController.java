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
            //TODO: 에러발생시 프론트에게 줄거 만들기
        }
        return ResponseEntity.ok(kakaoService.search(keyword));
    }

    @GetMapping("/main")
    public ResponseEntity<PlaceResponseDto> findRegionRestaurant() {
        //TODO: 강남역, 홍대입구역, 서울역 이렇게 세개 주고싶은데 어떻게 줄건지...?
        return ResponseEntity.ok(kakaoService.regionRestaurant("강남역"));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("오류발생오류발생 삐용삐용", e.getMessage()));
    }
}
