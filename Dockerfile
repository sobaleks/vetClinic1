FROM openjdk:19
RUN mkdir /app
WORKDIR /app
COPY target/app.jar /app
ENTRYPOINT java -jar /app/app.jar