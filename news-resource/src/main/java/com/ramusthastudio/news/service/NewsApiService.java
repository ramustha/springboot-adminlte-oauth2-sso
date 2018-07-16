package com.ramusthastudio.news.service;

import com.ramusthastudio.news.domain.Category;
import com.ramusthastudio.news.domain.Channel;
import com.ramusthastudio.news.domain.NewsApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsApiService {
  boolean isExistsByNewsId(String aNewsId);
  Page<NewsApi> getLatest(Pageable aPageable);
  Page<NewsApi> findAllFullContent(String aContain, Pageable aPageable);
  Page<NewsApi> findAllCategory(String aContain, Pageable aPageable);
  Page<NewsApi> getFeatures(Pageable aPageable);
  Page<Channel> getChannel(Pageable aPageable);
  Page<Category> getCategories(Pageable aPageable);
  NewsApi getNewsApiById(Long aId);
  NewsApi saveNewsApi(NewsApi aNewsApi);
  Iterable<NewsApi> saveNewsApi(Iterable<NewsApi> aNewsApis);
  void deleteNewsApi(Long aId);
  long count();
}
