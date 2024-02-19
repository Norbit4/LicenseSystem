FROM gradle:jdk17 AS builder

WORKDIR /backend

COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN gradle build

FROM eclipse-temurin:17-alpine 

WORKDIR /backend

COPY --from=builder /backend/build/libs/*.jar backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend.jar"]