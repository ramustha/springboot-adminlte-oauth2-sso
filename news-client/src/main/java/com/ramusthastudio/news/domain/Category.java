package com.ramusthastudio.news.domain;

import java.math.BigInteger;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Category {
  private String category;
  private BigInteger count;
}