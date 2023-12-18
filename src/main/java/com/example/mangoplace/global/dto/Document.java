package com.example.mangoplace.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Document {
    @JsonProperty("image_url")
    private String imageUrl;
}
