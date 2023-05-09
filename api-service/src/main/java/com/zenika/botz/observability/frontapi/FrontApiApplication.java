package com.zenika.botz.observability.frontapi;

import com.zenika.botz.observability.frontapi.clients.ClientsConfiguration;
import com.zenika.botz.observability.frontapi.web.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
        ClientsConfiguration.class,
        WebConfiguration.class
})
public class FrontApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(FrontApiApplication.class, args);
  }
}
