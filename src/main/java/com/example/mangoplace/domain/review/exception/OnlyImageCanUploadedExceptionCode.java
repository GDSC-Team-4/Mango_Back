package com.example.mangoplace.domain.review.exception;

import com.example.mangoplace.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OnlyImageCanUploadedExceptionCode implements ExceptionCode {

    ONLY_IMAGE_CAN_UPLOADED_EXCEPTION(HttpStatus.NOT_FOUND, "ONLY_IMAGE_CAN_UPLOADED", "JPG,PNG,JPEG만 업로드 가능합니다");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
