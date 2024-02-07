package com.uahannam.mediation.service;

import org.springframework.context.ApplicationEventPublisher;

public class EventProducer {

    private static ApplicationEventPublisher eventPublisher;

    public static void publishEvent(Object eventObject) {
        eventPublisher.publishEvent(eventObject);
    }

    // ApplicationEventPublisher를 초기화해주는 메서드를 외부에서 호출해야 합니다.
    public static void setEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        eventPublisher = applicationEventPublisher;
    }

}