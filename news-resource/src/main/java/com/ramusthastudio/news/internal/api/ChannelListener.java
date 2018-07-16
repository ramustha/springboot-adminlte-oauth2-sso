package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.domain.ReasonCat;
import com.ramusthastudio.news.internal.domain.News;

public interface ChannelListener {
  void onParsingError(Throwable aE, ReasonCat aReason);
  void onFailedLoadContent(Throwable aE, ReasonCat aReason, News aNews);
  void onFailedExtractContent(Throwable aE, ReasonCat aReason, News aNews);
  void onContentNotFound(ReasonCat aReason, String aQuery, News aNews);
  void onContentTooShort(ReasonCat aReason, String aQuery, News aNews);
  void onSuccess(News aNews);
}
