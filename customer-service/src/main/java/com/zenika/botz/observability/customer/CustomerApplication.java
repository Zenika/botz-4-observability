package com.zenika.botz.observability.customer;

import com.zenika.botz.observability.customer.business.BusinessConfiguration;
import com.zenika.botz.observability.customer.web.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
        BusinessConfiguration.class,
        WebConfiguration.class
})
public class CustomerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }

}
