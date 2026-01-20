# ETAPA 1: Compilación (Usamos Maven porque el repo es Maven)
FROM maven:3.8.6-openjdk-11-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ETAPA 2: Runtime (La imagen que tú querías, ligera y antigua)
FROM openjdk:8u181-jdk-alpine
EXPOSE 8080
WORKDIR /app
# Copiamos desde /target/ porque ahí es donde Maven guarda el archivo
COPY --from=builder /app/target/*.jar /app/spring-boot-application.jar
# Forzamos la vulnerabilidad para la demo
ENV LOG4J_FORMAT_MSG_NO_LOOKUPS=false
CMD ["java", "-jar", "/app/spring-boot-application.jar"]
