FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/bibliotheque-univ-1.0-SNAPSHOT.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-Dserver.port=10000", "-jar", "app.jar"]
