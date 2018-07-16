package com.ramusthastudio.news.internal.api;

import com.ramusthastudio.news.domain.ReasonCat;
import com.ramusthastudio.news.internal.domain.News;
import com.ramusthastudio.news.internal.domain.xml.Channel;
import com.ramusthastudio.news.internal.domain.xml.Rss;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramusthastudio.news.internal.api.ExtractorHelper.intoNews;

public final class Flowables {
  private static final Logger LOG = LoggerFactory.getLogger(Flowables.class);

  private Flowables() {}

  public static Flowable<Channel> debug(Flowable<Rss> aFlowable) {
    return aFlowable.subscribeOn(Schedulers.io())
        .onErrorResumeNext(aE -> {
          LOG.error("Failed parsing xml, {}", aE.getMessage());
          return Flowable.empty();
        })
        .flatMap(aRss -> aRss.channel != null ?
            Flowable.just(aRss.channel) : Flowable.empty());
  }

  public static Flowable<News> exec(Flowable<Rss> aFlowable, ChannelListener aListener) {
    return aFlowable.subscribeOn(Schedulers.io())
        .onErrorResumeNext(aE -> {
          LOG.error("Failed parsing xml, {}", aE.getMessage());
          aListener.onParsingError(aE, ReasonCat.FAILED_PARSING);
          return Flowable.empty();
        })
        .flatMap(aRss -> {
          if (aRss.channel != null) {
            return Flowable.just(intoNews(aRss.channel));
          }
          aListener.onParsingError(new Throwable("Failed merge into news"), ReasonCat.FAILED_MERGE);
          LOG.error("Failed merge into news");
          return Flowable.empty();
        })
        .flatMap(Flowable::fromIterable)
        .flatMap(aNews -> RetrofitService.raw(aNews.getLink())
            .onErrorResumeNext(aE -> {
              LOG.error("Failed load content, {}", aE.getMessage());
              aListener.onFailedLoadContent(aE, ReasonCat.FAILED_LOAD_CONTENT, aNews);
              return Flowable.empty();
            })
            .map(aResponseBody -> IndonesiaExtractor.contentExtractor(aNews, aResponseBody.string(), aListener))
            .onErrorResumeNext(aE -> {
              LOG.error("Failed extract content , {}", aE.getMessage());
              aListener.onFailedExtractContent(aE, ReasonCat.FAILED_EXTRACT_CONTENT, aNews);
              return Flowable.empty();
            }))
        .map(aNews -> {
          if (aNews.getMedia() != null) {
            List<String> media = aNews.getMedia();
            media.removeIf(aS -> aS.isEmpty() || !aS.startsWith("http"));
            aNews.setMedia(media.stream()
                .map(IndonesiaExtractor::imageReplacer)
                .distinct()
                .collect(Collectors.toList()));
          }
          return aNews;
        });
  }

  public static Disposable setupSubscriber(Flowable<Rss> aObservable, ChannelListener aListener) {
    return Flowables.exec(aObservable, aListener)
        .doOnSubscribe(aDisposable -> LOG.info("Starting"))
        .doOnError(RetrofitService::handleOnErrorTarget)
        .doOnNext(aNews -> {
          aListener.onSuccess(aNews);

          String id = aNews.getId();
          String chTitle = aNews.getChannelTitle();
          String title = aNews.getTitle();
          Instant pubDate = aNews.getPubDate();
          Instant createdTime = aNews.getCreatedTime();

          String b = "=====\n" +
              "Id:" + id + "\n" +
              "Channel:" + chTitle + "\n" +
              "Title:" + title + "\n" +
              "PubDate:" + pubDate + "\n" +
              "CreatedTime:" + createdTime + "\n" +
              "=====\n";

          LOG.info(b);
        })
        .doOnComplete(() -> LOG.info("Completed"))
        .subscribe();
  }
}
