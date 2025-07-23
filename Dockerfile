# 1) Stage de build con Maven + OpenJDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /workspace
# Copia solo el pom primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código y compila el fat-jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Stage de runtime con JDK Slim (para healthcheck + tu app)
FROM openjdk:17-jdk-slim AS runtime

# Instalamos curl para el healthcheck
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /workspace/target/spring-ecomerce-0.0.1-SNAPSHOT.jar app_ecomerce.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app_ecomerce.jar"]
