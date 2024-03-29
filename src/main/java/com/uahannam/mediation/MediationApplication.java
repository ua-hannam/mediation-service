package com.uahannam.mediation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MediationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediationApplication.class, args);
    }

}
