package com.example.mangoplace.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GrapeException extends RuntimeException {

    ExceptionCode errorCode;
    public GrapeException(ExceptionCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
