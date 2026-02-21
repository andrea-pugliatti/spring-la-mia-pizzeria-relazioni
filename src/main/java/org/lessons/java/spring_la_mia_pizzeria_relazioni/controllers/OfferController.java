package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.OfferRepository;
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
@RequestMapping("/offers")
public class OfferController {
    private OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer offerId, Model model) {
        Optional<Offer> offer = offerRepository.findById(offerId);

        if (offer.isEmpty()) {
            return "redirect:/pizzas";
        }

        model.addAttribute("offer", offer.get());

        return "offers/show";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "offers/create";
        }

        Offer offer = offerRepository.save(offerForm);

        return "redirect:/pizzas/" + offer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer offerId, Model model) {
        Optional<Offer> offer = offerRepository.findById(offerId);

        if (offer.isEmpty()) {
            return "redirect:/pizzas";
        }

        model.addAttribute("offer", offer.get());

        return "/offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "/offers/edit";
        }

        Offer offer = offerRepository.save(offerForm);

        return "redirect:/pizzas/" + offer.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer offerId, Model model) {
        Integer pizzaId = offerRepository.findById(offerId).get().getPizza().getId();

        offerRepository.deleteById(offerId);

        return "redirect:/pizzas/" + pizzaId;
    }

}
