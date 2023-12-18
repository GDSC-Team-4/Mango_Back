package com.example.mangoplace.global.util.dto;

import com.example.mangoplace.global.dto.KakaoResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageResponseDto {
    private KakaoResponseDto.Meta meta;
    @JsonProperty("documents")
    private List<Document> documents = new ArrayList<>();
}
