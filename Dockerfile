FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/mediation-service-0.0.1.jar mediationService.jar
ENTRYPOINT ["java", "-jar", "mediationService.jar"]
