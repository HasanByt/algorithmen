# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder

ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

WORKDIR /app
COPY . .

# Optional: settings.xml einfügen, falls nötig (für GitHub Package Registry)
# COPY .maven/settings.xml /root/.m2/settings.xml

RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
