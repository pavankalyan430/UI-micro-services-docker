# Use official OpenJDK base image
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built jar from Gradle
COPY build/libs/product-service-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
