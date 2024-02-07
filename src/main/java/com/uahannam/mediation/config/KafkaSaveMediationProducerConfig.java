package com.uahannam.mediation.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.uahannam.mediation.dto.KafkaSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;
import java.util.Objects;

@EnableKafka   // Kafka를 사용하기 위한 애너테이션
@Configuration
@RequiredArgsConstructor
public class KafkaSaveMediationProducerConfig {

    private final Environment environment;


    /*
     *  Bean의 이름을 설정하는 이유 -> 메시지를 보낼때 데이터 저장, 수정 삭제 등 다양한 이유로 보낼 수 있기 때문에 타입으로 찾으면 겹칠 수 있음
     *
     *  SaveUserKafkaDto 클래스 -> 메시지를 보낼때 데이터를 담을 객체를 제네릭 타입으로 넣어주면 됩니다.
     *  ㄴ> DefaultKafkaProducerFactory<String, 메시지를 보낼때 사용할 객체의 타입>
     */
    @Bean(name = "saveMediationDataProducerFactory")
    DefaultKafkaProducerFactory<String, KafkaSaveDto> saveMediationDataProducerFactory() {
        return new DefaultKafkaProducerFactory<>(saveMediationDataProducerConfig());
    }

    /*
     * DefaultKafkaProducerFactory를 구성하기 위한 설정 정보를 담는 Map을 등록해주어야 합니다.
     * -> 서버의 주소는 예제라서 간단하게 Environment 인터페이스에서 가져오는 방법을 선택했는데 ConfigurationProperty를 사용하는 것도 좋을 거 같습니다.
     */
    @Bean(name = "saveMediationDataProducerConfig")
    Map<String, Object> saveMediationDataProducerConfig() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Objects.requireNonNull(environment.getProperty("spring.kafka.producer.bootstrap-servers")),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
    }

    /*
     * Kafka로 메시지를 보낼 때 사용하는 KafkaTemplate 설정입니다.
     * -> 메시지를 보낼 때 사용할 객체를 KafkaTemplate<String, 메시지를 보낼 때 사용할 타입>으로 선언해주면 됩니다.
     */
    @Bean(name = "saveMediationKafkaTemplate")
    KafkaTemplate<String, KafkaSaveDto> saveMediationKafkaTemplate() {
        return new KafkaTemplate<>(saveMediationDataProducerFactory());
    }
}
