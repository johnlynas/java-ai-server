# syntax=docker/dockerfile:1
ARG BASE_DIR=..

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY ${BASE_DIR}/pom.xml ./pom.xml
COPY shared-contracts/pom.xml shared-contracts/pom.xml
COPY app-server/pom.xml app-server/pom.xml
COPY ai-agent/pom.xml ai-agent/pom.xml
COPY mcp-server/pom.xml mcp-server/pom.xml
RUN mvn -B -q dependency:go-offline || true
COPY shared-contracts/ shared-contracts/
COPY mcp-server/ mcp-server/
RUN mvn -B clean package -pl mcp-server -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/mcp-server/target/mcp-server-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
