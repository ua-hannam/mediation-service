package com.uahannam.mediation.service;

import com.uahannam.mediation.dto.KafkaSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMediationDataEventListener {

    // Bean으로 등록한 KafkaTemplate를 생성자 주입으로 받아줍니다.
    private final KafkaTemplate<String, KafkaSaveDto> saveUserKafkaTemplate;

    @Async // 이벤트를 비동기 처리하기 위한 애너테이션
    @EventListener(KafkaSaveDto.class) // 이벤트를 발행할때 사용한 Object의 타입을 적어줍니다.
    public void handleSaveEvent(KafkaSaveDto kafkaSaveDto) {

        // Kafka-Topic의 이름과 메시지 전송에 이용할 객체를 넣어줍니다.
        saveUserKafkaTemplate.send("save-mediation-data", kafkaSaveDto);
    }
}
