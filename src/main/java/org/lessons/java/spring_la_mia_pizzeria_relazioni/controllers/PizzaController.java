package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.OfferRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    private PizzaRepository pizzaRepository;
    private OfferRepository offerRepository;
    private IngredientRepository ingredientRepository;

    public PizzaController(
            PizzaRepository pizzaRepository,
            OfferRepository offerRepository,
            IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.offerRepository = offerRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "q", required = false) String name) {
        List<Pizza> list;

        if (name == null) {
            list = pizzaRepository.findAll();
        } else {
            list = pizzaRepository.findByNameContaining(name);
        }

        model.addAttribute("pizzas", list);
        model.addAttribute("query", name);

        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") Integer pizzaId) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            return "redirect:/pizzas";
        }

        model.addAttribute("pizza", pizza.get());
        model.addAttribute("offers", pizza.get().getOffers());
        model.addAttribute("ingredients", pizza.get().getIngredients());
        return "pizzas/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/create";
        }

        pizzaRepository.save(pizzaForm);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer pizzaId) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        model.addAttribute("ingredients", ingredientRepository.findAll());

        if (pizza.isEmpty()) {
            model.addAttribute("pizza", new Pizza());
            return "redirect:/pizzas/create";
        }

        model.addAttribute("pizza", pizza.get());
        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/edit";
        }

        pizzaRepository.save(pizzaForm);

        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") Integer pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId).get();

        List<Offer> offers = offerRepository.findByPizza(pizza);

        for (Offer offer : offers) {
            offerRepository.delete(offer);
        }

        pizzaRepository.deleteById(pizzaId);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/create-offer")
    public String getCreateOffer(@PathVariable("id") Integer pizzaId, Model model) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            return "redirect:/pizzas";
        }

        Offer newOffer = new Offer();
        newOffer.setPizza(pizza.get());

        model.addAttribute("offer", newOffer);

        return "offers/create";
    }

}
