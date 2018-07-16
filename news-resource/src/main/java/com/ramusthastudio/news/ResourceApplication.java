package com.ramusthastudio.news;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SpringBootApplication
public class ResourceApplication extends ResourceServerConfigurerAdapter {
  @Profile("!cloud")
  @Bean
  RequestDumperFilter requestDumperFilter() {
    return new RequestDumperFilter();
  }

  public static void main(String[] args) {
    SpringApplication.run(ResourceApplication.class, args);
  }
}
