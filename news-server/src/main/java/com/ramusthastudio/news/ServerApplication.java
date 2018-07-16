package com.ramusthastudio.news;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ServerApplication {
  public static void main(String[] aArgs) {
    SpringApplication.run(ServerApplication.class, aArgs);
  }

  @Profile("!cloud")
  @Bean
  RequestDumperFilter requestDumperFilter() {
    return new RequestDumperFilter();
  }
}
