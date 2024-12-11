# Build phase
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copy các file cần thiết vào container
COPY pom.xml /app/
COPY src /app/src/

# Build ứng dụng
RUN mvn clean package -DskipTests

# Runtime phase
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ build phase sang runtime container
COPY --from=build /app/target/*.jar app.jar

# Expose cổng 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
