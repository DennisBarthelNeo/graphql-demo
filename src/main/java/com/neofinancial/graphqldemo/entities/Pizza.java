package com.neofinancial.graphqldemo.entities;

import java.time.YearMonth;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document("pizzas")
@Data
@AllArgsConstructor
@Builder
public class Pizza {

  @Id
  private String id;
  private String name;
  private List<String> toppingIds;
  private YearMonth yearMonth;
}
