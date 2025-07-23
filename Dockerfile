# 1) Stage de build con Maven
FROM maven:3.8.7-openjdk-17 AS build

WORKDIR /workspace
# Copiamos sólo pom.xml primero para cachear dependencias
COPY pom.xml .
# Si tienes un settings.xml o certificados, también aquí

RUN mvn dependency:go-offline -B

# Ahora copiamos el código y compilamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Stage de runtime ligero con JRE
FROM openjdk:17-jdk-slim

# Instalamos curl para el healthcheck
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app
# Consumimos el JAR del primer stage
COPY --from=build /workspace/target/spring-ecomerce-0.0.1-SNAPSHOT.jar app_ecomerce.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app_ecomerce.jar"]