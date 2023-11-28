package com.neofinancial.graphqldemo.api.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateTopping {
  private String name;
  private BigDecimal priceCents;
}
