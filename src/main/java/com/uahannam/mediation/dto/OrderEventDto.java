package com.uahannam.mediation.dto;

public record OrderEventDto(
        String eventUUID,
        Long orderId
) {}
