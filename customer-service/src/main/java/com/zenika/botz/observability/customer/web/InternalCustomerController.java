package com.zenika.botz.observability.customer.web;

import com.zenika.botz.observability.contracts.InternalAddressService;
import com.zenika.botz.observability.contracts.CustomerAddress;
import com.zenika.botz.observability.contracts.CustomerIdentity;
import com.zenika.botz.observability.contracts.InternalIdentityService;
import com.zenika.botz.observability.customer.business.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InternalCustomerController implements InternalAddressService, InternalIdentityService {

  private static final Logger LOGGER = LoggerFactory.getLogger(InternalCustomerController.class);

  private final CustomerRepository customerRepository;

  public InternalCustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CustomerAddress getCustomerAddress(long customerId) {
    LOGGER.info("Getting address of customer {}", customerId);
    CustomerAddress result = this.customerRepository.findById(customerId)
            .map(customer -> new CustomerAddress(customerId, customer.address()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + customerId + " not found"));
    LOGGER.info("Got address of customer {}: {}", customerId, result);
    return result;
  }

  @Override
  public CustomerIdentity getCustomerIdentity(long customerId) {
    LOGGER.info("Getting identity of customer {}", customerId);
    CustomerIdentity result = this.customerRepository.findById(customerId)
            .map(customer -> new CustomerIdentity(customerId, customer.name()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + customerId + " not found"));
    LOGGER.info("Got identity of customer {}: {}", customerId, result);
    return result;
  }

}
