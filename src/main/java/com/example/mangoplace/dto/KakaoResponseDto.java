package com.example.mangoplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KakaoResponseDto  {
    private Meta meta;
    @JsonProperty("documents")
    private List<KakaoPlace> kakaoPlaces = new ArrayList<>();

    @Data
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;
        @JsonProperty("same_name")
        private SameName sameName;

    }

    @Data
    public static class SameName {
        private List<String> region;
        private String keyword;
        @JsonProperty("selected_region")
        private String selectedRegion;
    }
}

