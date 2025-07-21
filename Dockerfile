# ---------- Build Layer ----------
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# GitHub Token sicher setzen
ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

# settings.xml mit Auth-Daten kopieren
COPY settings.xml /root/.m2/settings.xml

# Projektdateien kopieren
COPY . .

# Maven-Build
RUN mvn clean package -DskipTests
