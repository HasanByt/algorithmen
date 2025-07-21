FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Token als Build-ARG (wird von docker-compose übergeben)
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

ENV GITHUB_USERNAME=${GITHUB_USERNAME}
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

COPY . .
COPY settings.xml /root/.m2/settings.xml

RUN mvn clean package -DskipTests

# ---------- Runtime Layer ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/algorithmen-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
