FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/CVService-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]