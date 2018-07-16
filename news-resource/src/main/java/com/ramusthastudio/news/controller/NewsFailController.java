package com.ramusthastudio.news.controller;

import com.ramusthastudio.news.domain.NewsFail;
import com.ramusthastudio.news.domain.Reason;
import com.ramusthastudio.news.repository.NewsFailRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableResourceServer
@RequestMapping("fail")
public class NewsFailController {
  @Autowired
  private NewsFailRepository fNewsFailRepository;

  @GetMapping("/getLatest")
  List<NewsFail> getLatest(Pageable aP) {
    if (aP.getPageSize() > 20) {
      return fNewsFailRepository.findAllByFixedFalseOrderByCreatedTimeDesc(
          new PageRequest(aP.getPageNumber(), 20));
    }
    return fNewsFailRepository.findAllByFixedFalseOrderByCreatedTimeDesc(aP);
  }

  @GetMapping("count")
  long count() {
    return fNewsFailRepository.count();
  }

  @GetMapping("/reasons")
  List<Reason> getReasons(Pageable aP) {
    List<Object[]> result;
    if (aP.getPageSize() > 20) {
      result = fNewsFailRepository.reasons(new PageRequest(aP.getPageNumber(), 20, aP.getSort()));
    } else {
      result = fNewsFailRepository.reasons(aP);
    }

    List<Reason> reasons = new ArrayList<>();
    for (Object[] objects : result) {
      reasons.add(new Reason(
          String.valueOf(objects[0]),
          (BigInteger) objects[1]));
    }
    return reasons;

  }
}
