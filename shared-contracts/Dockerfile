# syntax=docker/dockerfile:1
# Plain library JAR - not a runnable service. Built from the repo root;
# image exists so CI can publish/cache the artifact independently.
ARG BASE_DIR=..

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY ${BASE_DIR}/pom.xml ./pom.xml
COPY shared-contracts/ shared-contracts/
RUN mvn -B clean package -pl shared-contracts -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/shared-contracts/target/shared-contracts-*.jar shared-contracts.jar
# No ENTRYPOINT: this is a library, not a service.
