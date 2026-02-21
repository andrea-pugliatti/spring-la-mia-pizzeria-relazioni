package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.IngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String getIndex(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("new_ingredient", new Ingredient());

        return "ingredients/index";
    }

    @PostMapping("/create")
    public String postStore(
            @Valid @ModelAttribute("new_ingredient") Ingredient ingredientForm,
            BindingResult bindingResult,
            Model model) {

        if (!bindingResult.hasErrors()) {
            ingredientRepository.save(ingredientForm);
            model.addAttribute("new_ingredient", new Ingredient());
        }

        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "ingredients/index";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(Model model, @PathVariable("id") Integer ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();

        for (Pizza pizza : ingredient.getPizzas()) {
            pizza.getIngredients().remove(ingredient);
        }

        ingredientRepository.delete(ingredient);

        return "redirect:/ingredients";
    }

}
