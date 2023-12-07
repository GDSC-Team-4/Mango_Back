package com.example.mangoplace.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ExceptionResponseDTO {

    private final HttpStatus status;
    private final String code;
    private final String msg;

    public static ExceptionResponseDTO error(ExceptionCode exceptionCode) {
        return new ExceptionResponseDTO(
                exceptionCode.getStatus(),
                exceptionCode.getCode(),
                exceptionCode.getMsg()
        );
    }
}
