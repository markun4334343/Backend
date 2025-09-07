FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
# Add this fucking line to make mvnw executable
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests
ENTRYPOINT ["java", "-jar", "/app/target/Backend-0.0.1-SNAPSHOT.jar"]