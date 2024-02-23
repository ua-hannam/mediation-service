package com.uahannam.mediation.dto;

import com.uahannam.mediation.domain.Mediation;
import com.uahannam.mediation.domain.MediationEvent;

public record MediationKafkaDto(
        MediationInfo mediationInfo,
        MediationEventDto mediationEventDto
) {
    public Mediation toEntity() {
        return Mediation.builder()
                .id(mediationInfo.mediationId())
                .build();
    }

    public MediationEvent toEventEntity() {
        return MediationEvent.builder()
                .eventUUID(mediationEventDto.eventUUID())
                .mediationId(mediationInfo.mediationId())
                .build();
    }
}