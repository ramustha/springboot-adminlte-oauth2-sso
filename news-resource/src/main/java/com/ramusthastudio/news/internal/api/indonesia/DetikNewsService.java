package com.ramusthastudio.news.internal.api.indonesia;

import com.ramusthastudio.news.internal.domain.xml.Rss;
import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * http://rss.detik.com/rss.php
 *
 * API interfaces must not extend other interfaces.
 */
public interface DetikNewsService {
  @GET("index.php/detikcom")
  Flowable<Rss> detikcom();
}
