package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import com.example.mangoplace.global.exception.GrapeException;

public class RestaurantIdNotFoundException extends GrapeException {
    public RestaurantIdNotFoundException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
