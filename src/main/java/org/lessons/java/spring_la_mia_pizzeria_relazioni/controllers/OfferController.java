package org.lessons.java.spring_la_mia_pizzeria_relazioni.controllers;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.models.Offer;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
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

}
