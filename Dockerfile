FROM eclipse-temurin:24-jre-alpine

WORKDIR /app

# Copia apenas o JAR gerado pelo Maven
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
