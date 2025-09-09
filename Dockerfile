FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .

# Make mvnw executable and build
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# DEBUG: Show what files were created
RUN echo "=== LISTING TARGET DIRECTORY ==="
RUN ls -la target/
RUN echo "=== JAR FILE DETAILS ==="
RUN ls -la target/*.jar
RUN echo "=== JAR FILE NAME ==="
RUN find target -name "*.jar" -type f

ENTRYPOINT ["java", "-jar", "/app/target/Backend-0.0.1-SNAPSHOT.jar"]