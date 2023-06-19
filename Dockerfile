#FROM openjdk:17-jdk-slim-buster
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app

COPY .mvn/ ./mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]

#COPY app/build/lib/* build/lib/

#COPY app/build/libs/app.jar build/

#WORKDIR /app/build
#ENTRYPOINT java -jar app.jar