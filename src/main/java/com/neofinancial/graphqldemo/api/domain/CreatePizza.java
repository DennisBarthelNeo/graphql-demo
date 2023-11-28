package com.neofinancial.graphqldemo.api.domain;

import java.util.List;

import lombok.Data;

@Data
public class CreatePizza {
  private String name;
  private List<String> toppingsIds;
}
