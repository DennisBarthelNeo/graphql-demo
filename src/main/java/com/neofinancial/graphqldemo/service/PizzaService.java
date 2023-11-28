package com.neofinancial.graphqldemo.service;

import java.util.List;
import java.util.Optional;

import com.neofinancial.graphqldemo.api.domain.CreatePizza;
import com.neofinancial.graphqldemo.api.domain.UpdatePizza;
import com.neofinancial.graphqldemo.entities.Pizza;
import com.neofinancial.graphqldemo.mapper.PizzaMapper;
import com.neofinancial.graphqldemo.repositories.PizzaRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PizzaService {

  private final PizzaRepository pizzaRepository;
  private final PizzaMapper pizzaMapper;

  public List<Pizza> getAllPizzas() {
    List<Pizza> findAll = pizzaRepository.findAll();

    return findAll;
  }

  public Pizza createPizza(CreatePizza createPizza) {
    Pizza newPizza = pizzaMapper.toPizza(createPizza);

    return pizzaRepository.save(newPizza);
  }

  public Pizza updatePizza(UpdatePizza updatePizza) {
    Optional<Pizza> existingPizza = pizzaRepository.findById(updatePizza.getId());

    if (existingPizza.isPresent()) {
      pizzaMapper.addToExistingPizza(updatePizza, existingPizza.get());

      return pizzaRepository.save(existingPizza.get());
    }

    return null;
  }

  public String deletePizza(String pizzaId) {
    if (pizzaRepository.existsById(pizzaId)) {
      pizzaRepository.deleteById(pizzaId);

      return pizzaId;
    }

    return null;
  }
}
