FROM openjdk:17-jdk-alpine
LABEL maintainer="markskroba@gmail.com"
VOLUME /main-app
ADD target/devconnector-spring-boot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]