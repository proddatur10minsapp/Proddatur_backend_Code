# Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /proddaturiMinApp
COPY . .
RUN ls
RUN cd proddaturiMinApp
RUN ls
RUN mvn clean install -DskipTests

# Runtime Stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /proddaturiMinApp
RUN ls
COPY --from=build /target/proddaturiMinApp-3.4.3.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


