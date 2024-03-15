package com.uahannam.mediation.service;

import com.uahannam.mediation.domain.MediationEvent;
import com.uahannam.mediation.dto.MediationKafkaDto;
import com.uahannam.mediation.dto.OrderKafkaDto;
import com.uahannam.mediation.repository.MediationEventRepository;
import com.uahannam.mediation.repository.MediationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Slf4j(topic = "MediationService")
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class MediationService {

    private final MediationRepository mediationRepository;
    private final MediationEventRepository mediationEventRepository;
    private final SaveMediationEventListener saveMediationEventListener;

    @KafkaListener(topics = "order-topic", containerFactory = "orderKafkaListenerContainerFactory")
    public void handleOrderEvent(@Payload OrderKafkaDto orderKafkaDto) {
        MediationKafkaDto mediationKafkaDto = createMediationKafkaDto(orderKafkaDto);
        saveMediationEventListener.handleSaveEvent(mediationKafkaDto);
    }

    private MediationKafkaDto createMediationKafkaDto(OrderKafkaDto orderKafkaDto) {
        // 주문 정보를 기반으로 MediationKafkaDto 생성
        return null;
    }

    @TransactionalEventListener(MediationKafkaDto.class)
    public void handleMediationKafkaDto(MediationKafkaDto mediationKafkaDto) {
        saveMediationData(mediationKafkaDto);
        // 가게 서비스로 메시지 전송 로직 추가
    }

    public void saveMediationData(MediationKafkaDto mediationKafkaDto) {
        Optional<MediationEvent> mediationEvent = mediationEventRepository.findByEventUUID(mediationKafkaDto.mediationEventDto().eventUUID());

        if (mediationEvent.isEmpty()) {
            mediationRepository.save(mediationKafkaDto.toEntity());
            mediationEventRepository.save(mediationKafkaDto.toEventEntity());
        }
    }
}