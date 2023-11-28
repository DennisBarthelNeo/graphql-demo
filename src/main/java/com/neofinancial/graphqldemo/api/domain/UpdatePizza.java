package com.neofinancial.graphqldemo.api.domain;

import java.util.List;

import lombok.Data;

@Data
public class UpdatePizza {

  private String id;
  private String name;
  private List<String> toppingsIds;
}
