package com.zenika.botz.observability.common.tracing;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.observation.DefaultServerRequestObservationConvention;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.http.server.observation.ServerRequestObservationConvention;

@AutoConfiguration
@EnableConfigurationProperties(CustomTracingAutoConfiguration.CustomTracingProperties.class)
public class CustomTracingAutoConfiguration {

    @ConfigurationProperties("observability")
    public record CustomTracingProperties(String globalApplication){}

    @Bean
    public ServerRequestObservationConvention customTagsRequestObservationConvention(CustomTracingProperties tracingProperties){
        return new DefaultServerRequestObservationConvention(){
            @Override
            public KeyValues getLowCardinalityKeyValues(ServerRequestObservationContext context) {
                return super.getLowCardinalityKeyValues(context).and(KeyValue.of("application.name", tracingProperties.globalApplication()));
            }
        };
    }

}
