package com.ramusthastudio.news.domain;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Reason {
  private String reason;
  private BigInteger count;
}