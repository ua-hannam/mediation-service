package com.uahannam.mediation.dto;

import com.uahannam.mediation.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderKafkaDto {

    public OrderKafkaDto(Order order, List<OrderItem> orderItem, OrderEvent orderEvent) {
    }
}

record Order(Long orderId, Long memberId, String address, Long storeId, Integer totalPrice, OrderStatus orderStatus,
             LocalDateTime regDate, LocalDateTime modDate) {
}

/**
 * @param orderItemId Getters
 */
record OrderItem(Long orderItemId, Long orderId, Long itemId, Integer itemPrice, String itemName, Integer itemQuantity,
                 Integer itemTotalPrice, LocalDateTime regDate, LocalDateTime modDate) {
}

/**
 * @param eventUUID Getters
 */
record OrderEvent(String eventUUID, Long orderId) {
}
