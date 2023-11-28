package com.neofinancial.graphqldemo.repositories;

import com.neofinancial.graphqldemo.entities.Topping;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToppingRepository extends MongoRepository<Topping, String> {

}
