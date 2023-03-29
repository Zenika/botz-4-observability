# Tracing with Spring Cloud Sleuth, OpenTelemetry and Grafana

This code and content is based on [https://github.com/thombergs/code-examples/tree/master/spring-cloud/tracing], with its great companion article [Tracing with Spring Boot, OpenTelemetry, and Jaeger](https://reflectoring.io/spring-boot-tracing)

## Running and exploring this demo
 
- run `./mvnw clean install` to build the two Spring Boot applications (`api-service` and `customer-service`)
- create the dedicated docker network so that services can reach each other and grafana with `docker network create observability_tutorial`
- run `docker compose -f grafana/docker-compose.yml up -d` to start grafana in the background
- run the services with `docker compose up --build`
- call `http://localhost:8080/customers/<ID>` (where ID is a number from 1 to 50)
- open `http://localhost:3000/explore` in you browser while watching for the logs of the api-service.
- Notice the trace ids in the logs, second value into brackets. In grafana, go to the Explore View, select Tempo in the top left drop-down menu, and enter a Trace ID to see its content.

The above HTTP call goes to the `api-service`, which will call the `customer-service` for additional information. This will create a trace across both services, as should be evident in the logs with the same trace id.

The `docker compose` command also starts up an OpenTelemetry Collector, to which the Spring Boot apps send their traces. The OpenTelemetry Collector, in turn, sends the traces to Grafana.

## Switching to Kafka demo

- run `git switch kafka` 
- recompile with `./mvnw clean install`
- run `docker compose -f kafka/docker-compose.yml up -d` to start kafka in the background
- run the services with `docker compose up --build`
- start back with the same example  
