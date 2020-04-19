FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar

WORKDIR /opt/app

COPY ${JAR_FILE} demo-project.jar

ENTRYPOINT ["java","-jar","demo-project.jar"]
VOLUME /opt/app/data
EXPOSE 8080
