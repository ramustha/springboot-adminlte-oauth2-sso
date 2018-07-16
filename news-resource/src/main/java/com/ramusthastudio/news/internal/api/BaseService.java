package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.internal.domain.xml.Rss;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * API interfaces must not extend other interfaces.
 */
public interface BaseService {

  @GET
  Flowable<Rss> feedFlowable(@Url String aUrl);

  @GET
  Flowable<ResponseBody> rawFlowable(@Url String aUrl);
}
