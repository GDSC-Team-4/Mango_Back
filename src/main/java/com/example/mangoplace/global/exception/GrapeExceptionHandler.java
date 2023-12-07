package com.example.mangoplace.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mangoplace.global.exception.GrapeExceptionCode.BAD_REQUEST;
import static com.example.mangoplace.global.exception.GrapeExceptionCode.INTERNAL_SERVER_ERROR;

/*
TODO Global Exception 및 커스텀 Exception 처리를 합니다.
 */

@Slf4j
@RestControllerAdvice
public class GrapeExceptionHandler {

    /**
     * 예상한 예외들 잡는 곳
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
            GrapeException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleDefaultException(
            GrapeException e,
            HttpServletRequest request
    ) {
        log.error("custom error!!!! status : {} , errorCode : {}, message : {}, url : {}",
                e.getErrorCode().getStatus(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMsg(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(
                ExceptionResponseDTO.error(e.getErrorCode()),
                e.getErrorCode().getStatus()
        );
    }

    /**
     * 커스텀 에러를 제외한 bad Requset 만 중점으로 처리하는 에러 핸들러
     *
     * @param
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            InvalidFormatException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleBadRequest(
            Exception e, HttpServletRequest request
    ) {

        log.error("request error url : {}, message : {}",
                request.getRequestURI(),
                e.getMessage()
        );

        return new ResponseEntity<>(
                ExceptionResponseDTO.error(BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * @RequestParam valid 할 때 예외 처리
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
            ConstraintViolationException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleConstraintViolationException(
            ConstraintViolationException e, HttpServletRequest request
    ) {
        log.error("ConstraintViolationException error url : {}, message : {}",
                request.getRequestURI(),
                e.getMessage()
        );

        //searchTripListByKeyword.keyword: 검색어를 채워주세요 -> "검색어를 채워주세요" 반환.
        String[] msgList = e.getMessage().split(":");
        String msg = msgList[msgList.length-1].substring(1);

        return new ResponseEntity<>(
                new ExceptionResponseDTO(
                        BAD_REQUEST.getStatus(),
                        BAD_REQUEST.getCode(),
                        msg
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * @Valid 를 통해 나타나는 예외 처리
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {

        log.error("MethodArgumentNotValid error url : {}, message : {}",
                request.getRequestURI(),
                e.getMessage()
        );

        List<String> msgList = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ExceptionResponseDTO(
                        BAD_REQUEST.getStatus(),
                        BAD_REQUEST.getCode(),
                        String.join(", ", msgList)
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 기타 예외 처리
     * @param e
     * @param request
     * @return
     */

    @ExceptionHandler(value = {
            Exception.class, RuntimeException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.error("exception error class : {}, url : {}, message : {}"/*, trace : {}*/,
                e.getClass(),
                request.getRequestURI(),
                e.getMessage()
        );

        //개발중 예외처리 되지 않은 익셉션을 더 확실히 확인 할 수 있도록 스택트레이스 출력
        e.printStackTrace();

        return new ResponseEntity<>(
                ExceptionResponseDTO.error(INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
