FROM maven:3.8.6-eclipse-temurin-17 AS build

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy

ARG JAR_FILE=/home/app/target/*.jar

COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=test", "-jar","/app.jar"]