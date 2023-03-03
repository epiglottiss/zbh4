package com.zerobase.cms.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_ACCOUNT(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "없는 유저입니다."),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 등록된 계정입니다."),
    VERIFICATION_CODE_UNMATCH(HttpStatus.BAD_REQUEST, "코드가 다릅니다."),
    EXPIRED_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "만료된 코드 입니다."),



    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
