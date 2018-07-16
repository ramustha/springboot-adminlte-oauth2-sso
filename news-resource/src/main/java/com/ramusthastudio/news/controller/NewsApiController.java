package com.ramusthastudio.news.controller;

import com.ramusthastudio.news.domain.Category;
import com.ramusthastudio.news.domain.Channel;
import com.ramusthastudio.news.domain.NewsApi;
import com.ramusthastudio.news.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableResourceServer
public class NewsApiController {
  private final NewsApiService fNewsApiService;

  @Autowired
  public NewsApiController(NewsApiService aNewsApiService) {
    fNewsApiService = aNewsApiService;
  }

  @GetMapping("count")
  long count() {
    return fNewsApiService.count();
  }

  @GetMapping("features")
  Page<NewsApi> features(Pageable aP) {
    return fNewsApiService.getFeatures(aP);
  }

  @GetMapping("latest")
  Page<NewsApi> latest(Pageable aP) {
    return fNewsApiService.getLatest(aP);
  }

  @GetMapping("find")
  Page<NewsApi> find(@RequestParam(value = "q") String aQuery, Pageable aP) {
    return fNewsApiService.findAllFullContent(aQuery, aP);
  }

  @GetMapping("find/category")
  Page<NewsApi> category(@RequestParam(value = "q") String aQuery, Pageable aP) {
    return fNewsApiService.findAllCategory(aQuery, aP);
  }

  @GetMapping("channel")
  Page<Channel> channel(Pageable aP) {
    return fNewsApiService.getChannel(aP);
  }

  @GetMapping("categories")
  Page<Category> categories(Pageable aP) {
    return fNewsApiService.getCategories(aP);
  }

  @GetMapping("news/{id}")
  NewsApi newsById(@PathVariable(value = "id") Long aId) {
    return fNewsApiService.getNewsApiById(aId);
  }
}
