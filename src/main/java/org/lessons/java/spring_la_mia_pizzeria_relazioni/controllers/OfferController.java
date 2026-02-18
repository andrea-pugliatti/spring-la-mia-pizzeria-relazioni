package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private OfferRepository offerRepository;

    @PostMapping("/create")
    public String postStore(@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "offers/create";
        }

        offerRepository.save(offerForm);

        return "redirect:/pizzas/" + offerForm.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable("id") Integer offerId, Model model) {
        Optional<Offer> offer = offerRepository.findById(offerId);

        if (offer.isEmpty()) {
            return "redirect:/pizzas";
        }

        model.addAttribute("offer", offer.get());
        return "/offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String postUpdate(@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "/offers/edit";
        }

        offerRepository.save(offerForm);
        return "redirect:/pizzas/" + offerForm.getPizza().getId();
    }

}
