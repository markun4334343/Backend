FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package
CMD ["java", "-jar", "target/*.jar"]