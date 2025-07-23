FROM openjdk:17-jdk-slim

# instalar curl para el healthcheck
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

ARG JAR_FILE=target/spring-ecomerce-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_ecomerce.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app_ecomerce.jar"]
