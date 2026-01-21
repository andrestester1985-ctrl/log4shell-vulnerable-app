# ETAPA 1: Build con Gradle
FROM gradle:7.3.1-jdk17 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Compilamos el proyecto (esto genera el archivo .jar)
RUN gradle bootJar --no-daemon

# ETAPA 2: Runtime (Imagen ligera Java 8)
FROM openjdk:17-jdk-slim
EXPOSE 8080
WORKDIR /app
# El archivo generado por Gradle se guarda en build/libs/
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
# Forzamos la vulnerabilidad
ENV LOG4J_FORMAT_MSG_NO_LOOKUPS=false
CMD ["java", "-jar", "/app/spring-boot-application.jar"]