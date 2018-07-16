package com.ramusthastudio.news.controller;

import com.ramusthastudio.news.domain.Category;
import com.ramusthastudio.news.domain.Channel;
import com.ramusthastudio.news.domain.Find;
import com.ramusthastudio.news.domain.NewsApi;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import static com.ramusthastudio.news.util.Pageables.createPageableUri;

@Controller
@RequestMapping("news")
public class NewsApiController {

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

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  String showNewsApi(@PathVariable("id") int aId, Model aModel) {
    URI targetUrl = UriComponentsBuilder.fromUriString(resServer)
        .path("/news/" + aId)
        .build()
        .toUri();

    ResponseEntity<NewsApi> result = restTemplate.getForEntity(
        targetUrl,
        NewsApi.class);

    aModel.addAttribute("news", result.getBody());
    return "fragments/detail";
  }

  @RequestMapping(path = "/features", method = RequestMethod.GET)
  String features(
      Model aModel,
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    URI targetUrl = createPageableUri(resServer, "/features", aPage, aSize, aSort);

    ResponseEntity<NewsApiPage> result = restTemplate.getForEntity(
        targetUrl,
        NewsApiPage.class);
    Page<NewsApi> page = result.getBody();
    PageWrapper<NewsApi> pageWrapper = new PageWrapper<>(page, "/news/features");
    aModel.addAttribute("newsList", page.getContent());
    aModel.addAttribute("page", pageWrapper);
    aModel.addAttribute("title", "Features");
    aModel.addAttribute("path", "features/features");

    return "fragments/list";
  }

  @RequestMapping(path = "/latest", method = RequestMethod.GET)
  String latest(
      Model aModel,
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    URI targetUrl = createPageableUri(resServer, "/latest", aPage, aSize, aSort);

    ResponseEntity<NewsApiPage> result = restTemplate.getForEntity(
        targetUrl,
        NewsApiPage.class);
    Page<NewsApi> page = result.getBody();
    PageWrapper<NewsApi> pageWrapper = new PageWrapper<>(page, "/news/latest");
    aModel.addAttribute("newsList", page.getContent());
    aModel.addAttribute("page", pageWrapper);
    aModel.addAttribute("title", "Latest");
    aModel.addAttribute("path", "latest/latest");

    return "fragments/list";
  }

  @RequestMapping(path = "/find", method = RequestMethod.GET)
  String find(
      Model aModel,
      @RequestParam(value = "q", required = false) String aQuery,
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    if (aQuery != null) {
      URI targetUrl = UriComponentsBuilder.fromUriString(resServer)
          .path("/find")
          .queryParam("q", aQuery)
          .queryParam("page", aPage)
          .queryParam("size", aSize)
          .queryParam("sort", aSort)
          .build()
          .toUri();

      ResponseEntity<NewsApiPage> result = restTemplate.getForEntity(
          targetUrl,
          NewsApiPage.class);
      Page<NewsApi> page = result.getBody();
      PageWrapper<NewsApi> pageWrapper = new PageWrapper<>(page, "/news/find");
      aModel.addAttribute("newsList", page.getContent());
      aModel.addAttribute("page", pageWrapper);
    }

    aModel.addAttribute("find", new Find(aQuery));
    aModel.addAttribute("title", "Find");
    aModel.addAttribute("path", "find/find");

    return "fragments/find";
  }

  @RequestMapping(path = "/find/category", method = RequestMethod.GET)
  String category(
      Model aModel,
      @RequestParam(value = "q", required = false) String aQuery,
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    if (aQuery != null) {
      URI targetUrl = UriComponentsBuilder.fromUriString(resServer)
          .path("/find/category")
          .queryParam("q", aQuery)
          .queryParam("page", aPage)
          .queryParam("size", aSize)
          .queryParam("sort", aSort)
          .build()
          .toUri();

      ResponseEntity<NewsApiPage> result = restTemplate.getForEntity(
          targetUrl,
          NewsApiPage.class);
      Page<NewsApi> page = result.getBody();
      PageWrapper<NewsApi> pageWrapper = new PageWrapper<>(page, "/news/find/category");
      aModel.addAttribute("newsList", page.getContent());
      aModel.addAttribute("page", pageWrapper);
    }

    aModel.addAttribute("find", new Find(aQuery));
    aModel.addAttribute("title", "Category");
    aModel.addAttribute("path", "find/category");

    return "fragments/find";
  }

  @RequestMapping(path = "/channel", method = RequestMethod.GET)
  String channel(
      Model aModel,
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    URI targetUrl = createPageableUri(resServer, "/channel", aPage, aSize, aSort);

    ResponseEntity<ChannelPage> result = restTemplate.getForEntity(
        targetUrl,
        ChannelPage.class);
    Page<Channel> page = result.getBody();
    PageWrapper<Channel> pageWrapper = new PageWrapper<>(page, "/news/channel");
    aModel.addAttribute("channels", page.getContent());
    aModel.addAttribute("page", pageWrapper);

    return "news/channeltable";
  }

  @RequestMapping(path = "/categories", method = RequestMethod.GET)
  String categories(
      Model aModel,
      @RequestParam(value = "page", required = false, defaultValue = "0") int aPage,
      @RequestParam(value = "size", required = false, defaultValue = "20") int aSize,
      @RequestParam(value = "sort", required = false) String aSort) {

    URI targetUrl = createPageableUri(resServer, "/categories", aPage, aSize, aSort);

    ResponseEntity<CategoryPage> result = restTemplate.getForEntity(
        targetUrl,
        CategoryPage.class);
    Page<Category> page = result.getBody();
    PageWrapper<Category> pageWrapper = new PageWrapper<>(page, "/news/categories");
    aModel.addAttribute("categories", page.getContent());
    aModel.addAttribute("page", pageWrapper);

    return "news/categorytable";
  }
}
