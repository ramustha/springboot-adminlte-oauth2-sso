package com.ramusthastudio.news.service;

import com.ramusthastudio.news.domain.NewsApi;
import com.ramusthastudio.news.domain.NewsFail;
import com.ramusthastudio.news.domain.ReasonCat;
import com.ramusthastudio.news.internal.api.ChannelListener;
import com.ramusthastudio.news.internal.api.Flowables;
import com.ramusthastudio.news.internal.api.IndonesianService;
import com.ramusthastudio.news.internal.domain.News;
import com.ramusthastudio.news.repository.NewsApiRepository;
import com.ramusthastudio.news.repository.NewsFailRepository;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Example patterns:
 *
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
 * https://stackoverflow.com/questions/7979165/spring-cron-expression-for-every-after-30-minutes
 * https://www.freeformatter.com/cron-expression-generator-quartz.html
 *
 * +-------------------- second (0 - 59)
 * |  +----------------- minute (0 - 59)
 * |  |  +-------------- hour (0 - 23)
 * |  |  |  +----------- day of month (1 - 31)
 * |  |  |  |  +-------- month (1 - 12)
 * |  |  |  |  |  +----- day of week (0 - 6) (Sunday=0 or 7)
 * |  |  |  |  |  |  +-- year [optional]
 * |  |  |  |  |  |  |
 * *  *  *  *  *  *  * command to be executed
 */

@Component
public class FeedServices implements ChannelListener {
  private static final Logger LOG = LoggerFactory.getLogger(FeedServices.class);

  @Autowired
  private NewsApiRepository fNewsApiRepository;

  @Autowired
  private NewsFailRepository fNewsFailRepository;

  // @Scheduled(cron = "0 0/5 * * * ?") /* Running every 5 minute */
  // @Scheduled(cron = "0 0/50 * * * *") /* Running every 50 minute */
  @Scheduled(cron = "0 0/30 * * * *")
  public void loadIndonesianNews() {
    Flowables.setupSubscriber(IndonesianService.detikNews(), this);
  }

  @Override
  public void onParsingError(Throwable aE, ReasonCat aReason) { /*DO NOTHING*/ }

  @Override
  public void onFailedLoadContent(Throwable aE, ReasonCat aReason, News aNews) {
    if (!fNewsFailRepository.existsByNewsId(aNews.getId())) {
      LOG.error("Saved to repo: {} reason: {} ", aNews.getLink(), aE.getMessage());
      fNewsFailRepository.save(saveNewsFail(aReason, aE.getMessage(), aNews));
    }
  }

  @Override
  public void onFailedExtractContent(Throwable aE, ReasonCat aReason, News aNews) {
    if (!fNewsFailRepository.existsByNewsId(aNews.getId())) {
      LOG.error("Saved to repo: {} reason: {} ", aNews.getLink(), aE.getMessage());
      fNewsFailRepository.save(saveNewsFail(aReason, aE.getMessage(), aNews));
    }
  }

  @Override
  public void onContentNotFound(ReasonCat aReason, String aQuery, News aNews) {
    if (!fNewsFailRepository.existsByNewsId(aNews.getId())) {
      LOG.error("Saved to repo: {} reason: query not found {} ", aNews.getLink(), aQuery);
      fNewsFailRepository.save(saveNewsFail(aReason, aQuery, aNews));
    }
  }

  @Override
  public void onContentTooShort(ReasonCat aReason, String aQuery, News aNews) {
    if (!fNewsFailRepository.existsByNewsId(aNews.getId())) {
      LOG.error("Saved to repo: {} reason: content too short {} ", aNews.getLink(), aQuery);
      fNewsFailRepository.save(saveNewsFail(aReason, aQuery, aNews));
    }
  }

  @Override
  public void onSuccess(News aNews) {
    if (!fNewsApiRepository.existsByNewsId(aNews.getId())) {
      LOG.info("Saved to repo: {}", aNews.getLink());
      fNewsApiRepository.save(saveNewsApi(aNews));
    }
  }

  private static NewsApi saveNewsApi(News aNews) {
    return new NewsApi()
        .setNewsId(aNews.getId())
        .setChannelTitle(aNews.getChannelTitle())
        .setChannelDescription(aNews.getChannelDescription())
        .setChannelPubDate(Date.from(aNews.getChannelPubDate()))
        .setChannelImageLink(aNews.getChannelImageLink())
        .setChannelImageUrl(aNews.getChannelImageUrl())
        .setChannelName(aNews.getChannelName())
        .setTitle(aNews.getTitle())
        .setLink(aNews.getLink())
        .setCategory(aNews.getCategory())
        .setPubDate(Date.from(aNews.getPubDate()))
        .setCreatedTime(Date.from(aNews.getCreatedTime()))
        .setDescription(aNews.getDescription())
        .setMedia(aNews.getMedia())
        .setFullContent(aNews.getFullContent())
        .setCreatedTime(Date.from(aNews.getCreatedTime()));
  }

  private static NewsFail saveNewsFail(ReasonCat aReason, String aMessage, News aNews) {
    return new NewsFail()
        .setNewsId(aNews.getId())
        .setLink(aNews.getLink())
        .setPubDate(Date.from(aNews.getPubDate()))
        .setReason(aReason.name())
        .setReasonNote(aMessage)
        .setCreatedTime(new Date());
  }
}
