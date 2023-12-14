package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import com.example.mangoplace.global.exception.GrapeException;

public class ReviewIdNotFoundException extends GrapeException {
    public ReviewIdNotFoundException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
