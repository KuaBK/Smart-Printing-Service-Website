# Build phase
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY be/pom.xml /app/
COPY be/src /app/src/

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
