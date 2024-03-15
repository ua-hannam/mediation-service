package com.uahannam.mediation.service;

import com.uahannam.mediation.dto.MediationKafkaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SaveMediationEventListener {

    private final KafkaTemplate<String, MediationKafkaDto> saveMediationKafkaTemplate;

    @Async
    @TransactionalEventListener(MediationKafkaDto.class)
    public void handleSaveEvent(MediationKafkaDto mediationKafkaDto) {
        saveMediationKafkaTemplate.send("save-mediation-data", mediationKafkaDto);
    }
}