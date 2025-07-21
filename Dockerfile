# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# 👉 settings.xml für private GitHub-Pakete einfügen
COPY .maven/settings.xml /root/.m2/settings.xml

COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
