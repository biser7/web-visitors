FROM openjdk:17
WORKDIR /usr/src/app
LABEL maintainer="bisecom@gmail.com"
EXPOSE 8090
COPY target/*.jar web-visitors-app-1.0.jar
ENTRYPOINT ["java","-jar","/usr/src/app/web-visitors-app-1.0.jar"]