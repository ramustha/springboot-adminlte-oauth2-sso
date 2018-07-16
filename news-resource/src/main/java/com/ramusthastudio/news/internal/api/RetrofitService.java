/*
 * Copyright (c) 2014-2017 Archeta All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.internal.domain.Error;
import com.ramusthastudio.news.internal.domain.xml.Rss;
import io.reactivex.Flowable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public final class RetrofitService {
  private static final Logger LOG = LoggerFactory.getLogger(RetrofitService.class);
  private static final long DEFAULT_CONNECT_TIMEOUT = 20_000;
  private static final long DEFAULT_READ_TIMEOUT = 20_000;
  private static final long DEFAULT_WRITE_TIMEOUT = 20_000;
  private static final OkHttpClient fDefaultHttpClient;
  private static final BaseService sBaseService;

  private static long fDefaultConnectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT;
  private static long fDefaultReadTimeoutMillis = DEFAULT_READ_TIMEOUT;
  private static long fDefaultWriteTimeoutMillis = DEFAULT_WRITE_TIMEOUT;

  static {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(LOG::debug);
    interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

    fDefaultHttpClient = new OkHttpClient.Builder()
        .connectTimeout(fDefaultConnectTimeoutMillis, TimeUnit.MILLISECONDS)
        .readTimeout(fDefaultReadTimeoutMillis, TimeUnit.MILLISECONDS)
        .writeTimeout(fDefaultWriteTimeoutMillis, TimeUnit.MILLISECONDS)
        .addInterceptor(interceptor)
        .build();

    sBaseService = configure("http://ramusthastudio.com/", BaseService.class);
  }

  private RetrofitService() { }

  public static <T> T configure(String aBaseUrl, Class<T> aNewsFeed) {
    return new Retrofit.Builder()
        .baseUrl(aBaseUrl)
        .client(fDefaultHttpClient)
        .addCallAdapterFactory(RxErrorHandlingFactory.createAsync())
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        .build().create(aNewsFeed);
  }

  public static Flowable<Rss> feed(String aUrl) { return sBaseService.feedFlowable(aUrl); }

  public static Flowable<ResponseBody> raw(String aUrl) { return sBaseService.rawFlowable(aUrl); }

  public static Error handleOnErrorTarget(Throwable aThrowable) {
    if (aThrowable instanceof RetrofitException) {
      try {
        RetrofitException error = (RetrofitException) aThrowable;
        return error.getErrorBodyAs(Error.class);
      } catch (IOException aE) {
        LOG.error("Unable to convert the body to the specified type", aE);
      }
    }

    return new Error("error", "unexpected", aThrowable.getMessage());
  }
}
