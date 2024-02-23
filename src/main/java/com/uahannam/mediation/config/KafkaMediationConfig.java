package com.uahannam.mediation.config;

import com.uahannam.mediation.dto.MediationKafkaDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaMediationConfig {

    private final Environment environment;

    @Bean(name = "saveMediationProducerFactory")
    public ProducerFactory<String, MediationKafkaDto> saveMediationProducerFactory() {
        Map<String, Object> configProps = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers", "localhost:9092"),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "saveMediationKafkaTemplate")
    public KafkaTemplate<String, MediationKafkaDto> saveMediationKafkaTemplate() {
        return new KafkaTemplate<>(saveMediationProducerFactory());
    }

    @Bean(name = "saveMediationConsumerFactory")
    public ConsumerFactory<String, MediationKafkaDto> saveMediationConsumerFactory() {

        JsonDeserializer<MediationKafkaDto> deserializer = new JsonDeserializer<>(MediationKafkaDto.class, false);

        Map<String, Object> saveMediationDataConsumerConfig = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers", "localhost:9092"),
                ConsumerConfig.GROUP_ID_CONFIG, "save-mediation-data-group-id",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"
        );

        return new DefaultKafkaConsumerFactory<>(saveMediationDataConsumerConfig, new StringDeserializer(), deserializer);
    }

    @Bean(name = "saveMediationKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, MediationKafkaDto> saveMediationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MediationKafkaDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(saveMediationConsumerFactory());
        return factory;
    }

}