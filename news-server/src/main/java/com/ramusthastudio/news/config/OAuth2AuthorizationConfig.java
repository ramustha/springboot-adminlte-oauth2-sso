package com.ramusthastudio.news.config;

import java.security.KeyPair;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties({AuthorizationServerProperties.class})
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
  private final AuthenticationManager authenticationManager;
  private final AuthorizationServerProperties authorizationServerProperties;
  private final OAuth2ClientProperties oAuth2ClientProperties;

  @Autowired
  public OAuth2AuthorizationConfig(
      AuthenticationManager aAuthenticationManager,
      AuthorizationServerProperties aAuthorizationServerProperties,
      OAuth2ClientProperties aOAuth2ClientProperties) {
    authenticationManager = aAuthenticationManager;
    authorizationServerProperties = aAuthorizationServerProperties;
    oAuth2ClientProperties = aOAuth2ClientProperties;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient(oAuth2ClientProperties.getClientId())
        .secret(oAuth2ClientProperties.getClientSecret())
        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
        .scopes("news")
        .authorities("news_write", "news_read")
        .accessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1))
        .autoApprove(true);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .accessTokenConverter(accessTokenConverter());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security
        .tokenKeyAccess(authorizationServerProperties.getTokenKeyAccess())
        .checkTokenAccess(authorizationServerProperties.getCheckTokenAccess());
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    KeyPair keyPair = new KeyStoreKeyFactory(
        new ClassPathResource("jwt.jks"), "P@sswordramustha0".toCharArray()).getKeyPair("jwt");
    converter.setKeyPair(keyPair);
    return converter;
  }
}
