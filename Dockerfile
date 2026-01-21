# ETAPA 1: Build con Gradle
FROM gradle:7.3.1-jdk17 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Compilamos el proyecto (esto genera el archivo .jar)
RUN gradle bootJar --no-daemon

# ETAPA 2: Runtime (Imagen ligera Java 8)
FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080
WORKDIR /app

# El archivo generado por Gradle se guarda en build/libs/
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

# --- CONFIGURACIÓN PARA LA DEMO ---
# Forzamos la vulnerabilidad: permitimos que log4j procese "lookups"
ENV LOG4J_FORMAT_MSG_NO_LOOKUPS=false

CMD ["java", "-jar", "/app/spring-boot-application.jar"]