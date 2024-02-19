FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build ./build/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar