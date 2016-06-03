package com.workshop.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.Klant;
import com.workshop.service.KlantService;



@Controller
public class KlantController {
	
	@Autowired
	public KlantService klantService;
	
	public void toegang(){
		System.out.println("toegang tot controller?");
	}
	@RequestMapping ("/Klant")
    public ModelAndView listKlanten() {
		ModelAndView mav = new ModelAndView("Klant");
		List<Klant> klanten = klantService.listKlanten();
		mav.addObject("klanten", klanten);
		return mav;
    }
	
	@RequestMapping(value = { "/deleteKlant{id}" }, method = RequestMethod.GET)
    public String deleteKlant(@PathVariable long id){
		this.klantService.deleteKlant(id);
        return "redirect:/Klant";
    }
	
	@RequestMapping(value = { "/addKlant" }, method = RequestMethod.POST)
	public String addKlant(@ModelAttribute("Klant")Klant k){
		if (k.getId() == 0){
			this.klantService.addKlant(k);
		}
		
		return "redirect:/Klant";
	}
}