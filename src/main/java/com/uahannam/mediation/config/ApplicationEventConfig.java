package com.uahannam.mediation.config;

import com.uahannam.mediation.service.EventProducer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationEventConfig {

    /*
     * ApplicationContext 인터페이스를 생성자로 주입받아서, EventProducer에 주입합니다.
     * 설정 클래스 안에서 주입이 가능하기 때문에 InitializingBean 인터페이스를 등록하는 코드를 이용해
     * EventProducer.setEventPublisher() 메서드를 호출했습니다.
     */
    @Bean
    InitializingBean initializingBean(ApplicationContext applicationContext) {
        return () -> EventProducer.setEventPublisher(applicationContext);
    }
}
