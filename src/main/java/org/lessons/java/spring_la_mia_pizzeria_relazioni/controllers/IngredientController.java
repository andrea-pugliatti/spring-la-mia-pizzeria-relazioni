package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.IngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

}
