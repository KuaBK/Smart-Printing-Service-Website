# Build phase
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Chỉ định thư mục làm việc trong container
WORKDIR /app

# Copy file pom.xml vào container để Maven có thể tải các dependencies
COPY be/pom.xml /app/

# Copy thư mục src từ thư mục be vào container
COPY be/src /app/src/

# Build ứng dụng bằng Maven
RUN mvn clean package -DskipTests

# Runtime phase
FROM openjdk:17-jdk-slim

# Chỉ định thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build từ phase build sang container
COPY --from=build /app/target/*.jar /app/app.jar

# Expose cổng mà ứng dụng sẽ chạy (giả sử ứng dụng Spring Boot chạy trên cổng 8080)
EXPOSE 8080

# Lệnh chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
