# Web Visitors App

The Spring MVC application allows user to analyse visitors number of a specific site.\
To create a report file with name `visitors.csv` should be included to the project resources folder.\
File structure is `email, phone, source` \
The application supports huge volume input files processing concept.

## Tech
Java 17\
Sprint Boot 2.7.7\
Spring Mvc / Thymeleaf\
JPA\
MySQL 8\
Spring Batch\
Maven\
Mock Mvc \
Lombok

## Installation
Clone or unzip downloaded project from the following repository:\
<https://github.com/biser7/web-visitors/tree/develop> \
Run the Maven project with `IJ Idea`

## Tests
Controller is covered by Mock Mvc tests

## How to use
Run the application using your favorite IDE (it was designed via Idea IJ)\
or run the following command from project root directory:\
`mvn clean install`
`docker-compose up -d`
After the server is being started, the result could be checked via favorite browser\
using the following host: `localhost:8090`\
The application requires availability of run `MySql:8` server.\
It can be run via docker, and the following commands (in case of run via IDE):\
`docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8`\
The application allows setting file path, delimiter and page size via `application.properties` file.\
It has two pages:
- The first page allows the user to process file, that was already loaded\
  to the server, check the report, and delete already preloaded DB records.\
  While the job is in progress, all buttons are blocked to avoid extra clicks.
- The second page shows visitors report and comes back to the first page.

### TODO
Implement pagination via Thymeleaf.\
Currently, result is hardcoded for the first 1000 rows. It can be changed via `application.properties`\
settings.

## License
MIT License