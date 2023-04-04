FROM openjdk:11 as build
ARG JAR_FILE=target/*.jar
COPY target/QuanLyKTX-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
