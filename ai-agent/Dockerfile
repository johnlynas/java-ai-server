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
COPY ai-agent/ ai-agent/
RUN mvn -B clean package -pl ai-agent -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/ai-agent/target/ai-agent-*.jar app.jar
EXPOSE 8081
# Env: OPENAI_API_KEY, OPENAI_BASE_URL, OPENAI_MODEL, MCP_SERVER_URL, MCP_CLIENT_ENABLED
ENTRYPOINT ["java", "-jar", "app.jar"]
