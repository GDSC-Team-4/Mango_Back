package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UnAuthorizedExceptionCode implements ExceptionCode {
    UnAuthorizedException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this.", "권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
