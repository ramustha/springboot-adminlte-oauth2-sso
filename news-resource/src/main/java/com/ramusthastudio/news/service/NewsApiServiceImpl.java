package com.ramusthastudio.news.service;

import com.ramusthastudio.news.domain.Category;
import com.ramusthastudio.news.domain.Channel;
import com.ramusthastudio.news.domain.NewsApi;
import com.ramusthastudio.news.repository.NewsApiRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class NewsApiServiceImpl implements NewsApiService {
  private static final int MAX_SIZE = 20;
  private final NewsApiRepository fRepository;

  @Autowired
  public NewsApiServiceImpl(NewsApiRepository aRepository) {
    fRepository = aRepository;
  }

  @Override
  public boolean isExistsByNewsId(String aNewsId) {
    return fRepository.existsByNewsId(aNewsId);
  }

  @Override
  public Page<NewsApi> getLatest(Pageable aP) {
    if (aP.getPageSize() > MAX_SIZE) {
      return fRepository.findAllByOrderByPubDateDesc(
          new PageRequest(aP.getPageNumber(), MAX_SIZE));
    }
    return fRepository.findAllByOrderByPubDateDesc(aP);
  }

  @Override
  public Page<NewsApi> findAllFullContent(String aContain, Pageable aP) {
    if (aP.getPageSize() > MAX_SIZE) {
      return fRepository.findAllByFullContentContainingOrderByPubDateDesc(
          aContain,
          new PageRequest(aP.getPageNumber(), MAX_SIZE));
    }
    return fRepository.findAllByFullContentContainingOrderByPubDateDesc(aContain, aP);
  }

  @Override
  public Page<NewsApi> findAllCategory(String aContain, Pageable aP) {
    if (aP.getPageSize() > MAX_SIZE) {
      return fRepository.findAllByCategoryContainingOrderByPubDateDesc(
          aContain,
          new PageRequest(aP.getPageNumber(), MAX_SIZE));
    }
    return fRepository.findAllByCategoryContainingOrderByPubDateDesc(aContain, aP);
  }

  @Override
  public Page<NewsApi> getFeatures(Pageable aP) {
    Page<NewsApi> result;
    if (aP.getPageSize() > MAX_SIZE) {
      result = fRepository.features(new PageRequest(aP.getPageNumber(), MAX_SIZE));
    } else {
      result = fRepository.features(aP);
    }
    return result;
  }

  @Override
  public Page<Channel> getChannel(Pageable aP) {
    Page<Object[]> result;
    if (aP.getPageSize() > MAX_SIZE) {
      result = fRepository.channel(new PageRequest(aP.getPageNumber(), MAX_SIZE, aP.getSort()));
    } else {
      result = fRepository.channel(aP);
    }

    List<Channel> channels = new ArrayList<>();
    for (Object[] objects : result) {
      channels.add(new Channel(
          String.valueOf(objects[0]),
          String.valueOf(objects[1]),
          (Date) objects[2],
          (BigInteger) objects[3])
      );
    }

    Pageable pageable = new PageRequest(result.getNumber(), result.getSize(), result.getSort());
    return new PageImpl<>(channels, pageable, result.getTotalElements());
  }

  @Override
  public Page<Category> getCategories(Pageable aP) {
    Page<Object[]> result;
    if (aP.getPageSize() > MAX_SIZE) {
      result = fRepository.categories(new PageRequest(aP.getPageNumber(), MAX_SIZE, aP.getSort()));
    } else {
      result = fRepository.categories(aP);
    }

    List<Category> categories = new ArrayList<>();
    for (Object[] objects : result) {
      categories.add(new Category(
          String.valueOf(objects[0]),
          (BigInteger) objects[1]));
    }

    Pageable pageable = new PageRequest(result.getNumber(), result.getSize(), result.getSort());
    return new PageImpl<>(categories, pageable, result.getTotalElements());
  }

  @Override
  public NewsApi getNewsApiById(Long aId) {
    return fRepository.findOne(aId);
  }

  @Override
  public NewsApi saveNewsApi(NewsApi aNewsApi) {
    return fRepository.save(aNewsApi);
  }

  @Override
  public Iterable<NewsApi> saveNewsApi(Iterable<NewsApi> aNewsApis) {
    return fRepository.save(aNewsApis);
  }

  @Override
  public void deleteNewsApi(Long aId) {
    fRepository.delete(aId);
  }

  @Override
  public long count() {
    return fRepository.count();
  }
}
