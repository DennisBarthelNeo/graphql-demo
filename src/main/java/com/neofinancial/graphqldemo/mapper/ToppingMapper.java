package com.neofinancial.graphqldemo.mapper;

import java.util.Optional;

import com.neofinancial.graphqldemo.api.domain.CreateTopping;
import com.neofinancial.graphqldemo.api.domain.UpdateTopping;
import com.neofinancial.graphqldemo.entities.Topping;

import org.springframework.stereotype.Component;

@Component
public class ToppingMapper {

  public Topping toTopping(CreateTopping createTopping) {
    return Topping.builder()
        .name(createTopping.getName())
        .priceCents(createTopping.getPriceCents())
        .build();
  }

  public void addToExistingTopping(UpdateTopping updateTopping, Topping existingTopping) {
    Optional.ofNullable(updateTopping.getName()).ifPresent(existingTopping::setName);
    Optional.ofNullable(updateTopping.getPriceCents()).ifPresent(existingTopping::setPriceCents);
  }
}
