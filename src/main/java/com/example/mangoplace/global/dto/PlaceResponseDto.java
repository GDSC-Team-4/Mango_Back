package com.example.mangoplace.global.dto;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Transactional
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceResponseDto {
    private final List<Place> data;

    public static PlaceResponseDto from(List<Place> places) {
        return new PlaceResponseDto(places);
    }
}
