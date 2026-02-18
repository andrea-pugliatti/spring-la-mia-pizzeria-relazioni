package org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

}
