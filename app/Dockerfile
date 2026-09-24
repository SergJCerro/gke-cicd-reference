# Multi-stage build: compiles the jar inside the image itself, so this
# Dockerfile is self-contained and works with a plain `docker build .` —
# no external `mvn package` step required first (e.g. Render, local Docker).
# cloudbuild.yaml still runs `mvn package` separately for build caching, but
# doesn't depend on it: this Dockerfile would produce the same result alone.

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/target/sample-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
