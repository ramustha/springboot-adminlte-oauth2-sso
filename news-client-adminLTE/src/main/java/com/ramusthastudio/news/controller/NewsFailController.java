package com.ramusthastudio.news.controller;

import com.ramusthastudio.news.domain.NewsFail;
import com.ramusthastudio.news.domain.Reason;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.ramusthastudio.news.util.Pageables.createPageableUri;

@RestController
@RequestMapping("fail")
public class NewsFailController {

  @Autowired
  OAuth2RestTemplate restTemplate;

  @Value("${res-server}")
  String resServer;

  @RequestMapping(path = "/count", method = RequestMethod.GET)
  ResponseEntity<Long> count() {
    return restTemplate.getForEntity(
        resServer + "/count",
        Long.class);
  }

  @RequestMapping(path = "/latest", method = RequestMethod.GET)
  ResponseEntity<NewsFail[]> latest(
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    URI targetUrl = createPageableUri(resServer, "/latest", aPage, aSize, aSort);

    return restTemplate.exchange(
        targetUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<NewsFail[]>() {});
  }

  @GetMapping("reasons")
  ResponseEntity<Reason[]> getReasons(
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    URI targetUrl = createPageableUri(resServer, "/reasons", aPage, aSize, aSort);

    return restTemplate.exchange(
        targetUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Reason[]>() {});
  }
}
