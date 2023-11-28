package com.neofinancial.graphqldemo.mapper;

import java.util.Optional;

import com.neofinancial.graphqldemo.api.domain.CreatePizza;
import com.neofinancial.graphqldemo.api.domain.UpdatePizza;
import com.neofinancial.graphqldemo.entities.Pizza;

import org.springframework.stereotype.Component;

@Component
public class PizzaMapper {

  public Pizza toPizza(CreatePizza createPizza) {
    return Pizza.builder()
        .name(createPizza.getName())
        .toppingIds(createPizza.getToppingsIds())
        .build();
  }

  public void addToExistingPizza(UpdatePizza updatePizza, Pizza existingPizza) {
    Optional.ofNullable(updatePizza.getName()).ifPresent(existingPizza::setName);
    Optional.ofNullable(updatePizza.getToppingsIds()).ifPresent(existingPizza::setToppingIds);
  }
}
