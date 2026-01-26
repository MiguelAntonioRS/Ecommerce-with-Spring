# Usa Eclipse Temurin (OpenJDK oficial, compatible con Spring Boot 3)
FROM eclipse-temurin:17-jre-alpine

# Copia el JAR generado por Maven
COPY target/*.jar /app.jar

# Expone el puerto (Render inyecta $PORT)
EXPOSE $PORT

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "/app.jar"]