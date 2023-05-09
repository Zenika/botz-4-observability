package com.zenika.botz.observability.customer.web;

import com.zenika.botz.observability.customer.business.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public ErrorHandler errorHandler(){
        return new ErrorHandler();
    }

    @Bean
    public InternalCustomerController internalApiController(CustomerRepository customerRepository){
        return new InternalCustomerController(customerRepository);
    }

}
