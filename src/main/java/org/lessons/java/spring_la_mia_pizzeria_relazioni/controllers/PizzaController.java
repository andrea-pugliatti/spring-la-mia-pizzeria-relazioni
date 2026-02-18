package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.OfferRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping
    public String getIndex(Model model, @RequestParam(value = "q", required = false) String name) {
        List<Pizza> list = null;

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
    public String getShow(Model model, @PathVariable("id") Integer pizzaId) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            return "pizzas/404";
        }

        List<Offer> offers = offerRepository.findByPizza(pizza.get());

        model.addAttribute("pizza", pizza.get());
        model.addAttribute("offers", offers);
        return "pizzas/show";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {

        model.addAttribute("pizza", new Pizza());

        return "pizzas/create";
    }

    @PostMapping("/create")
    public String postStore(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/create";
        }

        pizzaRepository.save(pizzaForm);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(Model model, @PathVariable("id") Integer pizzaId) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            model.addAttribute("pizza", new Pizza());
            return "redirect:/pizzas/create";
        }

        model.addAttribute("pizza", pizza.get());
        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String postUpdate(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/edit";
        }

        pizzaRepository.save(pizzaForm);
        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String postMethodName(Model model, @PathVariable("id") Integer pizzaId) {
        pizzaRepository.deleteById(pizzaId);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/create-offer")
    public String getCreateOffer(@PathVariable("id") Integer pizzaId, Model model) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);

        if (pizza.isEmpty()) {
            return "pizzas/404";
        }

        Offer newOffer = new Offer();
        newOffer.setPizza(pizza.get());

        model.addAttribute("offer", newOffer);

        return "offers/create";
    }

}
