package com.ramusthastudio.news.util;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public final class Pageables {
  private Pageables() {}

  public static URI createPageableUri(String aUri, String aPath, int aPage, int aSize, String aSort) {
    return UriComponentsBuilder.fromUriString(aUri)
        .path(aPath)
        .queryParam("page", aPage)
        .queryParam("size", aSize)
        .queryParam("sort", aSort)
        .build()
        .toUri();
  }
}
