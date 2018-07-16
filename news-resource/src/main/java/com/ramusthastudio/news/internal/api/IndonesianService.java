/*
 * Copyright (c) 2014-2017 Archeta All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.internal.api.indonesia.DetikNewsService;
import com.ramusthastudio.news.internal.domain.xml.Rss;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramusthastudio.news.internal.api.RetrofitService.configure;

public final class IndonesianService {
  private static final Logger LOG = LoggerFactory.getLogger(IndonesianService.class);
  private static final DetikNewsService sDetikNewsService;

  static {
    sDetikNewsService = configure("http://rss.detik.com/", DetikNewsService.class);
  }

  private IndonesianService() { }

  @SuppressWarnings("unchecked")
  public static Flowable<Rss> detikNews() {
    LOG.info("Load detikNews");
    return Flowable.mergeArray(
        sDetikNewsService.detikcom()
    );
  }
}