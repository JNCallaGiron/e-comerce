# 1) Etapa de build con Maven + OpenJDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /workspace

# Copiar solo el pom.xml para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar el .jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Etapa de runtime
FROM openjdk:17-jdk-slim AS runtime

# Instalar curl para el healthcheck
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar el jar compilado
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
