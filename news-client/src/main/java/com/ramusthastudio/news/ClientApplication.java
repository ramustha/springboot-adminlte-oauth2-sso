package com.ramusthastudio.news;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@SpringBootApplication
@EnableOAuth2Sso
public class ClientApplication {
  public static void main(String[] aArgs) {
    SpringApplication.run(ClientApplication.class, aArgs);
  }

  @Bean
  public ClientCredentialsResourceDetails oauth2RemoteResource() {
    return new ClientCredentialsResourceDetails();
  }

  @Bean
  OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
      OAuth2ProtectedResourceDetails details) {
    return new OAuth2RestTemplate(details, oauth2ClientContext);
  }

  @Profile("!cloud")
  @Bean
  RequestDumperFilter requestDumperFilter() {
    return new RequestDumperFilter();
  }
}
