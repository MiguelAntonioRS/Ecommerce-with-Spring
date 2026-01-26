# Usa OpenJDK 17 (recomendado para Spring Boot 3)
FROM openjdk:17-jdk-slim

# Copia el JAR generado por Maven (asegúrate de que se llame app.jar o usa wildcard)
COPY target/*.jar /app.jar

# Expone el puerto (Render inyecta $PORT)
EXPOSE $PORT

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "/app.jar"]