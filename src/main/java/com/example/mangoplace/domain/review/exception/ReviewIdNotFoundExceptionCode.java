package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ReviewIdNotFoundExceptionCode implements ExceptionCode {
    REVIEW_ID_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "REVIEW_ID_NOT_FOUND_EXCEPTION", "리뷰ID를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
