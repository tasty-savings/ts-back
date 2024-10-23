# Step 1: Build the application with Gradle
FROM gradle:8.9.0-jdk17 AS build

# Work directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

RUN ./gradlew bootjar

# Step 2: Create a new image for the runtime
FROM openjdk:17-jdk-slim

# Work directory for runtime
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=local"]