package com.workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.Klant;
import com.workshop.service.KlantService;

@Controller
public class KlantController {

	@Autowired
	public KlantService klantService;

	public void toegang() {
		System.out.println("toegang tot controller?");
	}

	@RequestMapping(value = "/Klant")
	public ModelAndView listKlanten() {
		ModelAndView mav = new ModelAndView("Klant");
		List<Klant> klanten = klantService.listKlanten();
		mav.addObject("klanten", klanten);
		return mav;
	}

	@RequestMapping(value = { "/deleteKlant{id}" }, method = RequestMethod.GET)
	public String deleteKlant(@PathVariable long id) {
		this.klantService.deleteKlant(id);
		return "redirect:/Klant.html";
	}

	@RequestMapping(value = { "Klant/save" }, method = RequestMethod.POST)
	public String addKlant(@ModelAttribute("Klant") Klant k) {

		this.klantService.addKlant(k);

		return "redirect:/Klant.html";
	}

	@RequestMapping(value = {"Klant/findKlant"}, method =RequestMethod.GET)
	public ModelAndView findKlant(@RequestParam("id") String klant_id) {
		ModelAndView mav =  new ModelAndView("findKlant");
		Long id = Long.decode(klant_id);
		Klant klant = klantService.findKlant(id);
		mav.addObject("klant", klant);
		return mav;
	}

	

}