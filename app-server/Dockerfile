# syntax=docker/dockerfile:1
# Build the whole monorepo from the repo root, then package just this module.
ARG BASE_DIR=..

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY ${BASE_DIR}/pom.xml ./pom.xml
COPY shared-contracts/pom.xml shared-contracts/pom.xml
COPY app-server/pom.xml app-server/pom.xml
COPY ai-agent/pom.xml ai-agent/pom.xml
COPY mcp-server/pom.xml mcp-server/pom.xml
# Cache dependencies first (fast layer reuse)
RUN mvn -B -q dependency:go-offline || true
COPY shared-contracts/ shared-contracts/
COPY app-server/ app-server/
RUN mvn -B clean package -pl app-server -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/app-server/target/app-server-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
