# Build
FROM gradle:8.8.0-jdk17 AS build
WORKDIR /app

# Copy project files
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Build the application
RUN gradle build --no-daemon

# Run stage
# Example of custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:17-jdk-jammy as jre-build

# Create a custom Java runtime
RUN $JAVA_HOME/bin/jlink \
         --add-modules java.base \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# Define your base image
FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME

# Continue with your application deployment

WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

