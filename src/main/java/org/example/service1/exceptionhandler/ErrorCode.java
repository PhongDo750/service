package org.example.service1.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorCode {
    MESSAGE_NOT_SEND(404, "Không thể gửi tin nhắn"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message ;
}
