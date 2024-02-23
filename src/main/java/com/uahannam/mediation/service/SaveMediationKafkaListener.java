package com.uahannam.mediation.service;

import com.uahannam.mediation.dto.MediationKafkaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMediationKafkaListener {

    private final MediationService mediationService;

    @KafkaListener(topics = "save-mediation-data", containerFactory = "saveMediationKafkaListenerContainerFactory")
    void consumeMediationData(@Payload MediationKafkaDto mediationKafkaDto) {
        mediationService.saveMediationData(mediationKafkaDto);
    }
}
