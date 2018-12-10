FROM java:8

ENV SPRING_PROFILES_ACTIVE=production

ARG eurekaIp
ENV EUREKA_IP=${eurekaIp}

ADD target/gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
