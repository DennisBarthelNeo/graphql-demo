package com.neofinancial.graphqldemo.repositories;

import com.neofinancial.graphqldemo.entities.Pizza;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PizzaRepository extends MongoRepository<Pizza, String> {

}
