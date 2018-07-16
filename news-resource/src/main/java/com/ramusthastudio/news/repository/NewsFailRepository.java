package com.ramusthastudio.news.repository;

import com.ramusthastudio.news.domain.NewsFail;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFailRepository extends JpaRepository<NewsFail, Long> {
  boolean existsByNewsId(String aNewsId);
  List<NewsFail> findAllByFixedFalseOrderByCreatedTimeDesc(Pageable aPageable);

  @Query(value = "SELECT reason, count(reason) as count " +
      "FROM news_fail " +
      "GROUP BY reason " +
      "ORDER BY ?#{#pageable}", nativeQuery = true)
  List<Object[]> reasons(Pageable aPageable);
}
