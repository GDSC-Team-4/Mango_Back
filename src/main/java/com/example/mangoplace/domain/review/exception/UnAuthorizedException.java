package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import com.example.mangoplace.global.exception.GrapeException;

public class UnAuthorizedException extends GrapeException {

    public UnAuthorizedException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
