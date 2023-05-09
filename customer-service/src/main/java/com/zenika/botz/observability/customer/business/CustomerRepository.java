package com.zenika.botz.observability.customer.business;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(long id);
}
