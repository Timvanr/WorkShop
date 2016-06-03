package com.workshop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.*;
import com.workshop.service.*;

@Controller
public class BestellingController {

	@Autowired
	public BestellingService bestelService;
	@Autowired
	public ArtikelService artikelService;
	@Autowired
	public KlantService klantService;
	
	@RequestMapping ("Klant/maakBestelling")
    public ModelAndView listArtikelen() {
		ModelAndView mav = new ModelAndView("maakBestelling");
		List<Artikel> artikelen = artikelService.findAlleArtikelen();
		System.out.println(artikelen);
		mav.addObject("artikelen", artikelen);
		return mav;
    }

	@RequestMapping ("Klant/Bestelling")
    public ModelAndView listBestellingen() {
		ModelAndView mav = new ModelAndView("Bestelling");
		List<Bestelling> bestellingen = bestelService.listBestellingen();
		mav.addObject("bestellingen", bestellingen);
		return mav;
    }
	/*
	@RequestMapping ("Klant/findBestelling")
	public ModelAndView findBestelling(@RequestParam("id") String bestelling_id) {
		ModelAndView mav =  new ModelAndView("findKlant");
		Long id = Long.decode(bestelling_id);
		Bestelling bestelling = bestelService.findBestelling(id);
		System.out.println(bestelling);
		mav.addObject("bestelling", bestelling);
		return mav;
	}*/
	
	@RequestMapping(value = { "Klant/findArtikeleninBestelling{id}" }, method = RequestMethod.GET)
	public ModelAndView findArtikeleninBestelling(@PathVariable long id) {
		ModelAndView mav =  new ModelAndView("findBestelling");
		Bestelling bestelling = this.bestelService.findBestelling(id);
		Map<Artikel, Integer> artikelen = bestelling.getArtikelen();
		System.out.println(artikelen.toString());
		mav.addObject("artikelen", artikelen);
		return mav;   
    }
	
	@RequestMapping(value = { "Klant/findBestelling{id}" }, method = RequestMethod.GET)
	public ModelAndView findBestelling(@PathVariable long id) {
		ModelAndView mav =  new ModelAndView("findBestelling");
		Klant klant = this.klantService.findKlant(id);
		Set<Bestelling> bestelling = klant.getBestellingen();
		mav.addObject("bestelling", bestelling);
		return mav;   
    }
	/*
	@RequestMapping(value = { "Klant/maakBestelling" }, method = RequestMethod.GET)
	public String maakBestelling() {
		ModelAndView mav =  new ModelAndView("maakBestelling");
		return "maakBestelling";   
    }*/
	
}
