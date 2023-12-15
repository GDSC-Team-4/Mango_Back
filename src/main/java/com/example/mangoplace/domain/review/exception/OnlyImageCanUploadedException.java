package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import com.example.mangoplace.global.exception.GrapeException;

public class OnlyImageCanUploadedException extends GrapeException {
    public OnlyImageCanUploadedException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
