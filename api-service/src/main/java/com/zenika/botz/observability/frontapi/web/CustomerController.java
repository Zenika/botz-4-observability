package com.zenika.botz.observability.frontapi.web;

import com.zenika.botz.observability.contracts.CustomerAddress;
import com.zenika.botz.observability.contracts.CustomerIdentity;
import com.zenika.botz.observability.contracts.InternalAddressService;
import com.zenika.botz.observability.contracts.InternalIdentityService;
import com.zenika.botz.observability.frontapi.contracts.CustomerInformation;
import com.zenika.botz.observability.frontapi.contracts.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
  private final InternalIdentityService customerService;
  private final InternalAddressService addressService;


  public CustomerController(InternalIdentityService customerService, InternalAddressService addressService) {
    this.customerService = customerService;
    this.addressService = addressService;
  }

  @Override
  public CustomerInformation getCustomerById(long customerId) {
    LOGGER.info("get customer {} information", customerId);
    CustomerIdentity customer = customerService.getCustomerIdentity(customerId);
    CustomerAddress address = addressService.getCustomerAddress(customerId);
    CustomerInformation result = new CustomerInformation(customerId, customer.name(), address.address());
    LOGGER.info("got customer {} information: {}", customerId, result);
    return result;
  }

}
