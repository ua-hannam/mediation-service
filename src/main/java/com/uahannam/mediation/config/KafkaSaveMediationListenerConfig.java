package com.uahannam.mediation.config;

import com.uahannam.mediation.dto.KafkaSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka     // Kafka 활성화를 위해 필요한 애너테이션
@Configuration
@RequiredArgsConstructor
public class KafkaSaveMediationListenerConfig {

    private final Environment environment;

    /*
     *  Bean 이름을 등록하는 이유는, 여러 개의 Consumer 설정이 필요할 때 타입으로 구분하면 충돌이 일어날 수 있기 때문입니다.
     *  -> ConcurrentKafkaListenerContainerFactory<String, 메시지를 받을 타입(보낸 타입과 내용이 일치해야 한다)>로 선언합니다.
     */
    @Bean(name = "saveMediationDataKafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<String, KafkaSaveDto> saveMediationDataKafkaListenerContainerFactory() {
        // Producer-Service에서 사용한 SaveUserKafkaDto를 선언해서 같이 사용합니다 -> 필드와 구성요소가 같아야 역직렬화시 문제가 생기지 않는다.
        ConcurrentKafkaListenerContainerFactory<String, KafkaSaveDto> concurrentKafkaFactory =
                new ConcurrentKafkaListenerContainerFactory<>();

        concurrentKafkaFactory.setConsumerFactory(saveMediationDataKafkaListenerConsumerFactory());

        return concurrentKafkaFactory;
    }

    @Bean(name = "saveMediationDataKafkaListenerConsumerFactory")
    ConsumerFactory<String, KafkaSaveDto> saveMediationDataKafkaListenerConsumerFactory() {
        // JsonDeserializer 생성시 Kafka Header를 사용하지 않도록 설정했습니다 -> 헤더 사용시 Package 정보를 가져와서 클래스를 찾지 못한다는 에러를 발생시킵니다.
        JsonDeserializer<KafkaSaveDto> deserializer = new JsonDeserializer<>(KafkaSaveDto.class, false);

        Map<String, Object> saveMediationDataConsumerConfig = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"), // Kafka 서버의 주소를 받아옵니다.
                ConsumerConfig.GROUP_ID_CONFIG, "save-user-data",       // kafka group-id를 설정합니다.
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"
        );

        return new DefaultKafkaConsumerFactory<>(saveMediationDataConsumerConfig, new StringDeserializer(), deserializer);
    }
}
