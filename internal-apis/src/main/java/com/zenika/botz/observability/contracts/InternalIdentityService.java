package com.zenika.botz.observability.contracts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface InternalIdentityService {

    @GetMapping(path = "/customers/{id}/identity")
    CustomerIdentity getCustomerIdentity(@PathVariable("id") long customerId);
}
