package com.zenika.botz.observability.frontapi.clients;

import com.zenika.botz.observability.contracts.InternalAddressService;
import com.zenika.botz.observability.contracts.CustomerAddress;
import org.springframework.web.client.RestTemplate;

public class RestTemplateAddressClient implements InternalAddressService {

    private final RestTemplate restTemplate;
    private final String serviceUrl;

    RestTemplateAddressClient(
            RestTemplate restTemplate,
            String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public CustomerAddress getCustomerAddress(long customerId) {
        return restTemplate.getForObject(String.format("%s/customers/%d/address", serviceUrl, customerId), CustomerAddress.class);
    }

}
