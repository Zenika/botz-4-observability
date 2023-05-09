FROM eclipse-temurin:17-jre-alpine as builder

ARG maven_module

COPY $maven_module/target/$maven_module-*.jar                /tmp/app.jar

USER root

WORKDIR /tmp/work

RUN  java -Djarmode=layertools -jar /tmp/app.jar extract

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.18.0/opentelemetry-javaagent.jar /opt/opentelemetry-javaagent.jar

FROM eclipse-temurin:17-jre-alpine as runtime

ARG application_name

LABEL name="$application_name"                             \
      description="The $application_name"           \
      maintainer="cookie.monster@zenika.com"

COPY --from=builder /opt/opentelemetry-javaagent.jar  /opt/opentelemetry-javaagent.jar
COPY --from=builder /tmp/work/dependencies/           /opt/application/
COPY --from=builder /tmp/work/spring-boot-loader/     /opt/application/
COPY --from=builder /tmp/work/application/            /opt/application/

WORKDIR   /opt/application

ENV HOME                               "/opt/application"
ENV SPRING_APPLICATION_NAME            "$application_name"
ENV SPRING_PROFILES_INCLUDE             ""

ENV MEMORY_MIN                         "32m"
ENV MEMORY_MAX                         "256m"
ENV JVM_OPTIONS                        ""
ENV LISTEN_PORT                        "8080"


HEALTHCHECK  CMD     [ "curl", "--connect-timeout", "5", "http://localhost:$LISTEN_PORT/manage/health" ]

CMD  [ "sh", "-c", "java -Xms$MEMORY_MIN -Xmx$MEMORY_MAX $JVM_OPTIONS org.springframework.boot.loader.JarLauncher" ]
