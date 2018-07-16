/*
 * Copyright (c) 2014-2017 Archeta All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

package com.ramusthastudio.news.internal.api;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class RxErrorHandlingFactory extends CallAdapter.Factory {
  private final RxJava2CallAdapterFactory fOriginal;

  private RxErrorHandlingFactory() {
    this(false);
  }

  private RxErrorHandlingFactory(boolean aIsAsync) {
    if (aIsAsync) {
      fOriginal = RxJava2CallAdapterFactory.createAsync();
    } else {
      fOriginal = RxJava2CallAdapterFactory.create();
    }
  }

  private RxErrorHandlingFactory(Scheduler scheduler) {
    fOriginal = RxJava2CallAdapterFactory.createWithScheduler(scheduler);
  }

  @Override
  public CallAdapter<?, ?> get(Type aReturnType, Annotation[] aAnnotations, Retrofit aRetrofit) {
    return new RxCallAdapterWrapper<>(aRetrofit, fOriginal.get(aReturnType, aAnnotations, aRetrofit));
  }

  public static CallAdapter.Factory create() {
    return new RxErrorHandlingFactory();
  }

  public static CallAdapter.Factory createAsync() {
    return new RxErrorHandlingFactory(true);
  }

  private static CallAdapter.Factory createWithScheduler(Scheduler aScheduler) {
    return new RxErrorHandlingFactory(aScheduler);
  }

  private static class RxCallAdapterWrapper<R, T> implements CallAdapter<R, T> {
    private final Retrofit fRetrofit;
    private final CallAdapter<R, T> fWrapped;

    public RxCallAdapterWrapper(Retrofit aRetrofit, CallAdapter<R, T> aWrapped) {
      this.fRetrofit = aRetrofit;
      this.fWrapped = aWrapped;
    }

    @Override
    public Type responseType() {
      return fWrapped.responseType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T adapt(Call<R> aCall) {
      return (T) ((Flowable<T>) fWrapped.adapt(aCall)).onErrorResumeNext(aThrowable -> {
        return Flowable.error(retrofitException(aThrowable));
      });
    }

    private RetrofitException retrofitException(Throwable aThrowable) {
      // We had non-200 http error
      if (aThrowable instanceof HttpException) {
        HttpException httpException = (HttpException) aThrowable;
        Response<?> response = httpException.response();
        return RetrofitException.httpError(response.raw().request().url().toString(), response, fRetrofit);
      }

      // A network error happened
      if (aThrowable instanceof IOException) {
        return RetrofitException.networkError((IOException) aThrowable);
      }

      // We don't know what happened. We need to simply convert to an unknown error
      return RetrofitException.unexpectedError(aThrowable);
    }
  }
}
