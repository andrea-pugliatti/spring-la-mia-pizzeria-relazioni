package org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    public List<Offer> findByPizza(Pizza pizza);
}
