package com.ramusthastudio.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

  @Autowired
  OAuth2RestTemplate restTemplate;

  @Value("${res-server}")
  String resServer;

  @RequestMapping("/")
  String index(Model aModel) {

    long newsCount = restTemplate.getForEntity(
        resServer + "/count",
        Long.class).getBody();

    ChannelPage channels = restTemplate.getForEntity(
        resServer + "/channel?size=5",
        ChannelPage.class).getBody();

    CategoryPage categories = restTemplate.getForEntity(
        resServer + "/categories?size=5",
        CategoryPage.class).getBody();

    aModel.addAttribute("newsCount", newsCount);
    aModel.addAttribute("channels", channels);
    aModel.addAttribute("categories", categories);

    return "dashboard/index";
  }

  @RequestMapping("/docs")
  String docs() {
    return "docs";
  }

  @RequestMapping("/static")
  public RedirectView localRedirect() {
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl("pages/widgets.html");
    return redirectView;
  }
}
