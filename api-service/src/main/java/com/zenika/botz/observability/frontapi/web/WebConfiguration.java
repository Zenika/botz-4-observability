package com.zenika.botz.observability.frontapi.web;

import com.zenika.botz.observability.contracts.InternalAddressService;
import com.zenika.botz.observability.contracts.InternalIdentityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public CustomerController customerController(InternalIdentityService customerService, InternalAddressService addressService){
        return new CustomerController(customerService, addressService);
    }

    @Bean
    public ErrorHandler errorHandler(){
        return new ErrorHandler();
    }

}
