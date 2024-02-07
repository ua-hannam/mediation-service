package com.uahannam.mediation.service;

import com.uahannam.mediation.dto.KafkaSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMediationDataListener {

    private final MediationService mediationService;

    /*
     * Kafka 메시지를 소비하는 메서드를 정의하려면 @KafkaListener 애너테이션을 이용해 정의할 수 있습니다.
     * 애너테이션에는 메시지를 받을 토픽의 이름들과, group-id, 설정 클래스에서 작성한 containerFactory의 Bean 이름을 제공하면 됩니다.
     */
    @KafkaListener(topics = "save-mediation-data", groupId = "save-mediation-data", containerFactory = "saveMediationDataKafkaListenerContainerFactory")
    void consumeUserData(@Payload KafkaSaveDto mediationKafkaDto) {
        System.out.println(mediationKafkaDto);
//        mediationService.saveOrderHistory(userKafkaDto);
    }
}
