FROM maven:3.9.6-eclipse-temurin-21 AS builder

ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${env.GITHUB_TOKEN}

WORKDIR /app
COPY . .
COPY settings.xml /root/.m2/settings.xml

RUN mvn clean package -DskipTests

# ---------- Runtime Layer ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

