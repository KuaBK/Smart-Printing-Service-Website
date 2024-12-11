# Build phase
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copy toàn bộ tệp dự án cần thiết vào container
COPY pom.xml /app/
COPY src /app/src/

# Build dự án
RUN mvn clean package -DskipTests

# Runtime phase
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ build phase
COPY --from=build /app/target/*.jar app.jar

# Expose cổng 8080
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
