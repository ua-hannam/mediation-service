package com.uahannam.mediation.dto;

public record KafkaSaveDto(
        OrderInfo orderInfo,
        OrderEventDto orderEventDto
) {}
