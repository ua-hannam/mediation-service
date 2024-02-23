package com.uahannam.mediation.service;

import com.uahannam.mediation.domain.Mediation;
import com.uahannam.mediation.domain.MediationEvent;
import com.uahannam.mediation.dto.MediationEventDto;
import com.uahannam.mediation.dto.MediationInfo;
import com.uahannam.mediation.dto.MediationKafkaDto;
import com.uahannam.mediation.repository.MediationEventRepository;
import com.uahannam.mediation.repository.MediationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j(topic = "MediationService")
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class MediationService {

    private final MediationRepository mediationRepository;
    private final MediationEventRepository mediationEventRepository;

    public void saveMediationHistory() {
        Mediation mediation = Mediation.builder()
                .id(1L)
                .build();

        mediationRepository.save(mediation);

        EventProducer.publishEvent(createKafkaDto(mediation));
    }

    private MediationKafkaDto createKafkaDto(Mediation mediation) {

        MediationInfo mediationInfo = new MediationInfo(
                mediation.getId()
        );

        MediationEventDto mediationEventDto = new MediationEventDto(
                UUID.randomUUID().toString(),
                mediation.getId()
        );

        return new MediationKafkaDto(mediationInfo, mediationEventDto);
    }

    public void saveMediationData(MediationKafkaDto mediationKafkaDto) {
        Optional<MediationEvent> mediationEvent = mediationEventRepository.findByEventUUID(mediationKafkaDto.mediationEventDto().eventUUID());

        if (mediationEvent.isEmpty()) {
            mediationRepository.save(mediationKafkaDto.toEntity());
            mediationEventRepository.save(mediationKafkaDto.toEventEntity());
        }
    }
}
