package com.zerobase.cms.order.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomException.CustomExceptionResponsce> exceptionHandler(HttpServletRequest req, final CustomException e){
        return ResponseEntity.status(e.getStatus())
                .body(CustomException.CustomExceptionResponsce.builder()
                        .message(e.getMessage())
                        .code(e.getErrorCode().name())
                        .status(e.getStatus())
                        .build());
    }
}
