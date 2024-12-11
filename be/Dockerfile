# Start from a Maven base image
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml /app/
COPY src /app/src/

# Build the project
RUN mvn clean package -DskipTests

# Start a new image for running the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the previous build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
