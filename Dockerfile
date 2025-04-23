FROM openjdk:17-jdk-slim
RUN mkdir /app
WORKDIR /app
COPY target/vetClinic-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT java -jar /app/app.jar