# Observability with springboot and micrometer

## Running and exploring this demo
 
- create ad dedicated docker network `botz_4_observability`
- create ad dedicated docker volume `botz_4_observability`
- build with `maven` and `jdk-17` (you can use `docker` if you don't want to install `maven`: `docker container run --rm -u 1000:1000 -v .:/sources -w /sources  maven:3.8-eclipse-temurin-17-alpine mvn clean verify`)
- run `docker compose -f grafana/docker-compose.yml up -d` to start grafana in the background
- run the services with `docker compose up --build`
- call `http://localhost:8080/customers/<ID>` (where ID is a number from 1 to 4)
- open `http://localhost:3000/explore` in you browser while watching for the logs of the api-service.
- Notice the trace ids in the logs, second value into brackets. In grafana, go to the Explore View, select Tempo in the top left drop-down menu, and enter a Trace ID to see its content.

The above HTTP call goes to the `api-service`, which will call the `customer-service` for additional information. This will create a trace across both services, as should be evident in the logs with the same trace id.

