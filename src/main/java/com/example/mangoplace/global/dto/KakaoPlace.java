package com.example.mangoplace.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 카카오에서 받아오는 객체
 */
@Data
public class KakaoPlace {
    private String id;
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("category_group_code")
    private String categoryGroupCode;
    @JsonProperty("category_group_name")
    private String categoryGroupName;
    private String phone;
    @JsonProperty("address_name")
    private String addressName;
    @JsonProperty("road_address_name")
    private String roadAddressName;
    private String x;
    private String y;
    @JsonProperty("place_url")
    private String placeUrl;
    private String distance;
    @JsonProperty("image_url")
    private String imageUrl;
}
