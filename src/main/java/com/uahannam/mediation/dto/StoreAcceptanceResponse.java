package com.uahannam.mediation.dto;

public record StoreAcceptanceResponse(
        Long orderId,
        boolean accepted,
        String acceptanceDetails
        // Add other fields as needed
) {
}
