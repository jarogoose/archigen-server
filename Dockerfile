FROM openjdk:17-jdk-alpine

COPY ./build/libs/archigen-0.0.1.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
