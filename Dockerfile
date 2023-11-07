#FROM openjdk:17-jdk-slim-buster as build
#WORKDIR /app
#COPY . /app
#RUN ./mvnw clean package -DskipTests
#
#FROM openjdk:17-jre-slim-buster
#WORKDIR /app
#COPY --from=build /app/target/CVService-0.0.1-SNAPSHOT.jar /app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
FROM openjdk:17-jdk-slim AS MAVEN_BUILD
WORKDIR /app
COPY ./ ./
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /app/target/CVService-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]