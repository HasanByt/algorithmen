FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
COPY settings.xml /root/.m2/settings.xml

# Projektdateien kopieren
COPY . .

# Maven-Build
RUN mvn clean package -DskipTests

# ---------- Runtime Layer ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
