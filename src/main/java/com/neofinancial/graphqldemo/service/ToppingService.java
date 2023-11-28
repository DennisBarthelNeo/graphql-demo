package com.neofinancial.graphqldemo.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.neofinancial.graphqldemo.api.domain.CreateTopping;
import com.neofinancial.graphqldemo.api.domain.UpdateTopping;
import com.neofinancial.graphqldemo.entities.Pizza;
import com.neofinancial.graphqldemo.entities.Topping;
import com.neofinancial.graphqldemo.mapper.ToppingMapper;
import com.neofinancial.graphqldemo.repositories.ToppingRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToppingService {

  private final ToppingRepository toppingRepository;
  private final ToppingMapper toppingMapper;

  public List<Topping> getAllToppings() {
    return toppingRepository.findAll();
  }

  public Topping createTopping(CreateTopping createTopping) {
    Topping newTopping = toppingMapper.toTopping(createTopping);

    return toppingRepository.save(newTopping);
  }

  public Topping updateTopping(UpdateTopping updateTopping) {
    Optional<Topping> existingTopping = toppingRepository.findById(updateTopping.getId());

    if (existingTopping.isPresent()) {
      toppingMapper.addToExistingTopping(updateTopping, existingTopping.get());

      return toppingRepository.save(existingTopping.get());
    }

    return null;
  }

  public String deleteTopping(String toppingId) {
    if (toppingRepository.existsById(toppingId)) {
      toppingRepository.deleteById(toppingId);

      return toppingId;
    }

    return null;
  }

  public Map<Pizza, BigDecimal> getPriceCentsForPizzas(List<Pizza> pizzas) {
    List<String> allToppingsIds = pizzas.stream()
        .flatMap(pizza -> pizza.getToppingIds().stream())
        .distinct()
        .collect(Collectors.toList());

    Iterable<Topping> toppings = toppingRepository.findAllById(allToppingsIds);

    Spliterator<Topping> spliterator = toppings.spliterator();
    Map<String, Topping> toppingMap = StreamSupport.stream(() -> spliterator, spliterator.characteristics(), false)
        .collect(Collectors.toMap(Topping::getId, Function.identity()));

    Map<Pizza, BigDecimal> resultMap = new HashMap<>();
    for (Pizza pizza : pizzas) {
      BigDecimal priceForPizza = pizza.getToppingIds().stream()
          .map(toppingId -> toppingMap.get(toppingId).getPriceCents())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      resultMap.put(pizza, priceForPizza);
    }

    return resultMap;
  }

  public List<Topping> getToppingsByIds(List<String> toppingIds) {
    Iterable<Topping> toppings = toppingRepository.findAllById(toppingIds);

    Spliterator<Topping> spliterator = toppings.spliterator();
    return StreamSupport.stream(() -> spliterator, spliterator.characteristics(), false)
        .collect(Collectors.toList());
  }
}
