package com.example.mangoplace.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceResponseDto {
    private final List<Place> data;

    public static PlaceResponseDto from(List<Place> places) {
        return new PlaceResponseDto(places);
    }
}
