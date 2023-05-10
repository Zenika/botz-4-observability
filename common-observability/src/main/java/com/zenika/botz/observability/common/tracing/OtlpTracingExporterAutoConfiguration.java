package com.zenika.botz.observability.common.tracing;

import io.micrometer.tracing.otel.bridge.OtelTracer;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporterBuilder;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import org.springframework.boot.actuate.autoconfigure.tracing.ConditionalOnEnabledTracing;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AutoConfiguration
@ConditionalOnEnabledTracing
@ConditionalOnClass({OtelTracer.class, SdkTracerProvider.class, OpenTelemetry.class, OtlpHttpSpanExporter.class})
@EnableConfigurationProperties(OtlpTracingExporterAutoConfiguration.OtlpTracingProperties.class)
//TODO Remove me in springboot 3.1. See PR https://github.com/spring-projects/spring-boot/pull/34508
public class OtlpTracingExporterAutoConfiguration {

    @ConfigurationProperties("management.otlp.tracing")
    public record OtlpTracingProperties(@DefaultValue("http://localhost:4318/v1/traces") String endpoint,
                                        @DefaultValue("10s") Duration timeout,
                                        @DefaultValue("NONE") Compression compression,
                                        Map<String, String> headers) {

        enum Compression {

            /**
             * Gzip compression.
             */
            GZIP,

            /**
             * No compression.
             */
            NONE

        }

    }

    @Bean
    @ConditionalOnMissingBean(value = OtlpHttpSpanExporter.class,
            type = "io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter")
    OtlpHttpSpanExporter otlpHttpSpanExporter(OtlpTracingProperties properties) {
        OtlpHttpSpanExporterBuilder builder = OtlpHttpSpanExporter.builder()
                .setEndpoint(properties.endpoint())
                .setTimeout(properties.timeout())
                .setCompression(properties.compression().name().toLowerCase());
        for (Map.Entry<String, String> header : Optional.ofNullable(properties.headers()).orElseGet(Collections::emptyMap).entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        return builder.build();
    }

}
