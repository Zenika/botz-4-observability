package com.zenika.botz.observability.frontapi.clients;

import com.zenika.botz.observability.contracts.CustomerIdentity;
import com.zenika.botz.observability.contracts.InternalIdentityService;
import org.springframework.web.client.RestTemplate;

public class RestTemplateCustomerClient implements InternalIdentityService {

	private final RestTemplate restTemplate;
	private final String baseUrl;

	public RestTemplateCustomerClient(
			RestTemplate restTemplate,
			String baseUrl) {
		this.restTemplate = restTemplate;
		this.baseUrl = baseUrl;
	}

	@Override
	public CustomerIdentity getCustomerIdentity(long customerId){
		String url = String.format("%s/customers/%d/identity", baseUrl, customerId);
		return restTemplate.getForObject(url, CustomerIdentity.class);
	}

}
