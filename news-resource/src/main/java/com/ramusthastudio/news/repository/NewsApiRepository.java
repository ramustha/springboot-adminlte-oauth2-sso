package com.ramusthastudio.news.repository;

import com.ramusthastudio.news.domain.NewsApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsApiRepository extends JpaRepository<NewsApi, Long> {
  boolean existsByNewsId(String aNewsId);
  NewsApi findNewsApiByNewsId(String aNewsId);
  Page<NewsApi> findAllByOrderByPubDateDesc(Pageable aPageable);
  Page<NewsApi> findAllByFullContentContainingOrderByPubDateDesc(String aContain, Pageable aPageable);
  Page<NewsApi> findAllByCategoryContainingOrderByPubDateDesc(String aContain, Pageable aPageable);

  @Query(value = "WITH summary AS " +
      "(SELECT p.*, ROW_NUMBER() OVER(PARTITION BY p.channel_title , p.channel_name ORDER BY p.pub_date DESC) AS rk FROM news_api p) " +
      "SELECT s.* " +
      "FROM summary s " +
      "WHERE s.rk = 1 " +
      "ORDER BY s.pub_date DESC \n-- #pageable\n",
      countQuery = "WITH summary AS " +
          "(SELECT p.*, ROW_NUMBER() OVER(PARTITION BY p.channel_title , p.channel_name ORDER BY p.pub_date DESC) AS rk FROM news_api p) " +
          "SELECT count(s.*) " +
          "FROM summary s " +
          "WHERE s.rk = 1 ",
      nativeQuery = true)
  Page<NewsApi> features(Pageable aPageable);

  @Query(value = "SELECT channel_title, channel_name, MAX(pub_date) as pub_date, count(channel_title) as count " +
      "FROM news_api " +
      "GROUP BY channel_title, channel_name " +
      "ORDER BY count DESC \n-- #pageable\n",
      countQuery = "SELECT count(channel_title) " +
          "FROM news_api " +
          "GROUP BY channel_title, channel_name ",
      nativeQuery = true)
  Page<Object[]> channel(Pageable aPageable);

  @Query(value = "SELECT category, count(category) as count " +
      "FROM news_api " +
      "GROUP BY category " +
      "ORDER BY count DESC \n-- #pageable\n",
      countQuery = "SELECT count(category) " +
      "FROM news_api " +
      "GROUP BY category ",
      nativeQuery = true)
  Page<Object[]> categories(Pageable aPageable);
}
