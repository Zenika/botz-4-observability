package com.zenika.botz.observability.contracts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface InternalAddressService {

    @GetMapping(path = "/customers/{customerId}/address")
    @ResponseStatus(HttpStatus.OK)
    CustomerAddress getCustomerAddress(@PathVariable("customerId") long customerId);

}
