package com.uahannam.mediation.config;

import com.uahannam.mediation.service.EventProducer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationEventConfig {

    @Bean
    InitializingBean initializingBean(ApplicationContext applicationContext) {
        return () -> EventProducer.setEventPublisher(applicationContext);
    }
}
