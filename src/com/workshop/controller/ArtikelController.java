package com.workshop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ArtikelController {

	@Autowired
	public BestellingService bestelService;
	@Autowired
	public ArtikelService artikelService;
	
	
	@RequestMapping ("Klant/Artikel")
    public ModelAndView listArtikelen() {
		ModelAndView mav = new ModelAndView("Artikel");
		List<Artikel> artikelen = artikelService.listArtikelen();
		mav.addObject("artikelen", artikelen);
		return mav;
    }

}
