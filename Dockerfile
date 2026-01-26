# Etapa 1: Construir el JAR con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Construye el JAR (sin tests para velocidad)
RUN mvn -B package -DskipTests

# Etapa 2: Ejecutar el JAR con JRE ligero
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar /app.jar

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "/app.jar"]