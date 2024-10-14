# Use a base image with JDK
FROM openjdk:17-jdk-slim as builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build.gradle.kts files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .

# Copy your source code
COPY src src

# Make the Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build --no-daemon

# Use a lightweight image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
