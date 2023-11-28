package com.neofinancial.graphqldemo.api.controller;

import java.util.List;

import com.neofinancial.graphqldemo.api.domain.CreatePizza;
import com.neofinancial.graphqldemo.api.domain.UpdatePizza;
import com.neofinancial.graphqldemo.entities.Pizza;
import com.neofinancial.graphqldemo.repositories.PizzaRepository;
import com.neofinancial.graphqldemo.service.PizzaService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PizzaController {

  private final PizzaService pizzaService;

  @QueryMapping(name = "pizzas")
  public List<Pizza> getAllPizzas() {
    return pizzaService.getAllPizzas();
  }

  @MutationMapping
  public Pizza createPizza(@Argument("input") CreatePizza createPizza) {
    return pizzaService.createPizza(createPizza);
  }

  @MutationMapping
  public Pizza updatePizza(@Argument("input") UpdatePizza updatePizza) {
    return pizzaService.updatePizza(updatePizza);
  }

  @MutationMapping
  public String deletePizza(String id) {
    return pizzaService.deletePizza(id);
  }
}
