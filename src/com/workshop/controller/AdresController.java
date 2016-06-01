package com.workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.Adres;
import com.workshop.service.AdresService;

@Controller
public class AdresController {

	@Autowired
	private AdresService adresService;
	
	@RequestMapping ("/Adres")
    public ModelAndView listAdressen() {
		ModelAndView mav = new ModelAndView("Adres");
		List<Adres> adressen = adresService.listAdressen();
		mav.addObject("adressen", adressen);
		return mav;
    }
	
	@RequestMapping(value = { "/deleteAdres{id}" }, method = RequestMethod.GET)
    public String deleteAdres(@PathVariable long id){
		this.adresService.deleteAdres(id);
        return "redirect:/Adres.html";
    }

	@RequestMapping(value = { "Adres/find" }, method = RequestMethod.POST)
	public String findAdres(@PathVariable("adres") long id){		
		Adres adres = this.adresService.findAdres(id);
		System.out.println(adres.toString());
		return "redirect:/Adres.html";
	}
	
	
	@RequestMapping(value = { "Adres/save" }, method = RequestMethod.POST)
	public String addAdres(@ModelAttribute("adres") Adres adres){		
		this.adresService.addAdres(adres);			
		return "redirect:/Adres.html";
	}

}
