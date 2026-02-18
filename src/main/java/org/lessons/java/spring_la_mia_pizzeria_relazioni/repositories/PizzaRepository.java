package org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    public List<Pizza> findByNameContaining(String name);
}
