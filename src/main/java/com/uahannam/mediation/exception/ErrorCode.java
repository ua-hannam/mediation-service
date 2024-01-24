package com.uahannam.mediation.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {
    TIMEOUT_ERROR(HttpStatus.BAD_REQUEST, "Request timed out");

    public static final String ERROR_CODE_PREFIX = "ERR_";
    public static final String ERROR_CODE_SUFFIX = "_CODE";

    private final HttpStatus httpStatus;

    private final String message;

    public String getCode() {
        return ERROR_CODE_PREFIX + this.name() + ERROR_CODE_SUFFIX;
    }

}
