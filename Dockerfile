# Builder Stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# .env für den Build-Kontext (für docker-compose)
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

ENV GITHUB_USERNAME=${GITHUB_USERNAME}
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

COPY . .
COPY .maven/settings.xml /root/.m2/settings.xml

RUN mvn clean package -DskipTests
