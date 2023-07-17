package com.waffle.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원 입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원 입니다."),
    ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도 입니다."),

    // login
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해주세요."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증 시간이 만료 되었습니다."),

    // balance
    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "관리자에게 문의 해주세요.");

    private final HttpStatus httpStatus;
    private final String detail;
}
