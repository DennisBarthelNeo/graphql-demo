package com.neofinancial.graphqldemo.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.neofinancial.graphqldemo.api.domain.CreateTopping;
import com.neofinancial.graphqldemo.api.domain.UpdateTopping;
import com.neofinancial.graphqldemo.entities.Pizza;
import com.neofinancial.graphqldemo.entities.Topping;
import com.neofinancial.graphqldemo.service.ToppingService;

import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class ToppingController {

  @Autowired
  private ToppingService toppingService;

  public ToppingController(BatchLoaderRegistry registry) {
    registry.forTypePair(String.class, Topping.class).registerMappedBatchLoader((toppingIds, env) -> {
      Map<String, Topping> toppingMap = toppingService.getToppingsByIds(new ArrayList<>(toppingIds)).stream()
          .collect(Collectors.toMap(Topping::getId, Function.identity()));
      return Mono.just(toppingMap);
    });
    registry.forTypePair(Pizza.class, BigDecimal.class).registerMappedBatchLoader((pizzas, env) -> {
      Map<Pizza, BigDecimal> priceCentsForPizzas = toppingService.getPriceCentsForPizzas(new ArrayList<>(pizzas));
      return Mono.just(priceCentsForPizzas);
    });
  }

  @QueryMapping(name = "toppings")
  public List<Topping> getToppings() {
    return toppingService.getAllToppings();
  }

  @SchemaMapping(typeName = "Pizza", field = "toppings")
  public CompletableFuture<List<Topping>> getToppings(Pizza pizza,
      DataLoader<String, Topping> loader) {
    System.out.println(System.identityHashCode(loader));
    return loader.loadMany(pizza.getToppingIds());
  }

  // @SchemaMapping(typeName = "Pizza", field = "toppings")
  // public List<Topping> getToppings(Pizza pizza) {
  // return toppingService.getToppingsByIds(pizza.getToppingIds());
  // }

  @SchemaMapping(typeName = "Pizza", field = "priceCents")
  public CompletableFuture<BigDecimal> getPriceCents(Pizza pizza, DataLoader<Pizza, BigDecimal> loader) {
    System.out.println(System.identityHashCode(loader));
    return loader.load(pizza);
  }

  // @SchemaMapping(typeName = "Pizza", field = "priceCents")
  // public BigDecimal getPriceCents(Pizza pizza) {
  // return toppingService.getPriceCentsForToppings(pizza.getToppingIds());
  // }

  @MutationMapping
  public Topping createTopping(
      @Argument(name = "input") CreateTopping createTopping) {
    return toppingService.createTopping(createTopping);
  }

  @MutationMapping
  public Topping updateTopping(@Argument(name = "input") UpdateTopping updateTopping) {
    return toppingService.updateTopping(updateTopping);
  }

  @MutationMapping
  public String deleteTopping(@Argument("input") String id) {
    return toppingService.deleteTopping(id);
  }
}