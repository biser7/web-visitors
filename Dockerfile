FROM openjdk:17
LABEL maintainer="bisecom@gmail.com"
EXPOSE 8090
COPY target/*.jar web-visitors-app-1.0.jar
ENTRYPOINT ["java","-jar","/web-visitors-app-1.0.jar"]