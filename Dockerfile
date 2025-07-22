FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/spring-ecomerce-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_ecomerce.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_ecomerce.jar"]
