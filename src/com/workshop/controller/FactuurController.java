package com.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.Factuur;
import com.workshop.service.FactuurService;

public class FactuurController {

	@Autowired
	private FactuurService factuurService;
	
	@RequestMapping("/Factuur")
	public ModelAndView findFactuur(@RequestParam("id") long id){
		ModelAndView mav = new ModelAndView("Factuur");
		Factuur bestellingfactuur = factuurService.findFactuurByBestelling_id(id);
		mav.addObject(bestellingfactuur);
		return mav;
	}
}
