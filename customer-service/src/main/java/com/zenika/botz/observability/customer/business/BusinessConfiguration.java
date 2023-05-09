package com.zenika.botz.observability.customer.business;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties(BusinessConfiguration.CustomersProperties.class)
public class BusinessConfiguration {

    public record CustomerProperties(String name, String address){}

    @ConfigurationProperties("business")
    public record CustomersProperties(Map<Long, CustomerProperties> customers){}

    @Bean
    public CustomerRepository customerRepository(CustomersProperties customersProperties){
        return id -> Optional.ofNullable(customersProperties.customers().get(id))
                .map(customerProperties -> new Customer(id, customerProperties.name(),customerProperties.address()));
    }

}
