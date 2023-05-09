package com.zenika.botz.observability.frontapi.clients;

import com.zenika.botz.observability.contracts.InternalAddressService;
import com.zenika.botz.observability.contracts.InternalIdentityService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableConfigurationProperties(ClientsConfiguration.ClientsProperties.class)
public class ClientsConfiguration {
    @ConfigurationProperties("clients")
    public record ClientsProperties(String address, String identity){}

    @Bean
    public ResponseErrorHandler internalApiErrorHandler(){
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                byte [] buffer = new byte[128];
                try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    int byteRead;
                    InputStream body = response.getBody();
                    while ((byteRead = body.read(buffer)) > 0) {
                        baos.write(buffer, 0, byteRead);
                    }
                    throw new ResponseStatusException(response.getStatusCode(), baos.toString());
                }
            }
        };
    }

    @Bean
    public InternalAddressService addressService(RestTemplateBuilder builder,
                                                 @Qualifier("internalApiErrorHandler") ResponseErrorHandler responseErrorHandler,
                                                 ClientsConfiguration.ClientsProperties clientsProperties){
        return new RestTemplateAddressClient(builder
                .errorHandler(responseErrorHandler)
                .build(), clientsProperties.address());
    }

    @Bean
    public InternalIdentityService identityService(RestTemplateBuilder builder,
                                                   @Qualifier("internalApiErrorHandler") ResponseErrorHandler responseErrorHandler,
                                                   ClientsConfiguration.ClientsProperties clientsProperties){
        return new RestTemplateCustomerClient(builder
                .errorHandler(responseErrorHandler)
                .build(), clientsProperties.identity());
    }

}
