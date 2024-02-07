package com.uahannam.mediation.service;

import com.uahannam.mediation.domain.Mediation;
import com.uahannam.mediation.dto.KafkaSaveDto;
import com.uahannam.mediation.dto.OrderEventDto;
import com.uahannam.mediation.dto.OrderInfo;
import com.uahannam.mediation.repository.MediationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j(topic = "MediationService")
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class MediationService {

    private final MediationRepository mediationRepository;

    public void saveOrderHistory() {
        Mediation mediation = Mediation.builder()
                .id(1L)
                .build();

        mediationRepository.save(mediation);

        EventProducer.publishEvent(createKafkaDto(mediation));
    }

    private KafkaSaveDto createKafkaDto(Mediation mediation) {
        OrderInfo orderInfo = new OrderInfo(
                mediation.getId()
        );

        OrderEventDto orderEventDto = new OrderEventDto(
                UUID.randomUUID().toString(),
                mediation.getId()
        );

        return new KafkaSaveDto(orderInfo, orderEventDto);
    }

}
