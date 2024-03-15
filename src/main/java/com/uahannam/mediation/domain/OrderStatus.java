package com.uahannam.mediation.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {

    RECEIPT("주문 접수"),
    USER_CANCEL_REQUEST("사용자 취소 요청"),
    USER_CANCEL("사용자 취소"),
    STORE_CANCEL("가게 사정 취소"),
    APPROVAL("주문 승인");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}