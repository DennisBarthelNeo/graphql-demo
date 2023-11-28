package com.neofinancial.graphqldemo.entities;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document("toppings")
@Data
@AllArgsConstructor
@Builder
public class Topping {

  @Id
  private String id;

  private String name;

  private BigDecimal priceCents;
}
