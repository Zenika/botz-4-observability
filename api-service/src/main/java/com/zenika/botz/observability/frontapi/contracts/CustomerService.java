package com.zenika.botz.observability.frontapi.contracts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerService {

    @GetMapping(path = "/customers/{customerId}")
    CustomerInformation getCustomerById(@PathVariable("customerId") long customerId);

}
