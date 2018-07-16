/*
 * Copyright (c) 2014-2017 Archeta All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.internal.domain.Error;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RetrofitException extends RuntimeException {
  private final String fUrl;
  private final Response<?> fResponse;
  private final Kind fKind;
  private final Retrofit fRetrofit;

  /** Identifies the event fKind which triggered a {@link RetrofitException}. */
  public enum Kind {
    /** An {@link IOException} occurred while communicating to the server. */
    NETWORK,
    /** A non-200 HTTP status code was received from the server. */
    HTTP,
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
  }

  private RetrofitException(String aMessage, String aUrl, Response<?> aResponse, Kind aKind, Throwable aException, Retrofit aRetrofit) {
    super(aMessage, aException);
    fUrl = aUrl;
    fResponse = aResponse;
    fKind = aKind;
    fRetrofit = aRetrofit;
  }

  /**
   * HTTP fResponse body converted to specified {@code type}. {@code null} if there is no
   * fResponse.
   *
   * @throws IOException if unable to convert the body to the specified {@code type}.
   */
  @SuppressWarnings("unchecked")
  public <T> T getErrorBodyAs(Class<T> aType) throws IOException {
    if (fResponse == null || fResponse.errorBody() == null) {
      if (fKind == Kind.UNEXPECTED) {
        return (T) Error.defaultUnexpectedError();
      }
      return (T) Error.defaultNetworkError();
    }
    Converter<ResponseBody, T> converter = fRetrofit.responseBodyConverter(aType, new Annotation[0]);
    return converter.convert(fResponse.errorBody());
  }

  public static RetrofitException httpError(String aUrl, Response<?> aResponse, Retrofit aRetrofit) {
    String message = aResponse.code() + " " + aResponse.message();
    return new RetrofitException(message, aUrl, aResponse, Kind.HTTP, null, aRetrofit);
  }

  public static RetrofitException networkError(IOException aException) {
    return new RetrofitException(aException.getMessage(), null, null, Kind.NETWORK, aException, null);
  }

  public static RetrofitException unexpectedError(Throwable aException) {
    return new RetrofitException(aException.getMessage(), null, null, Kind.UNEXPECTED, aException, null);
  }
}
