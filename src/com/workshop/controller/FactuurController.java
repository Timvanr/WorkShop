package com.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.Factuur;
import com.workshop.service.FactuurService;
@Controller
public class FactuurController {

	@Autowired
	private FactuurService factuurService;
	
	@RequestMapping(value = "/Factuur")
	public ModelAndView findFactuur(){
		String message = "Hallo daar";
		ModelAndView mav = new ModelAndView("Factuur");
		mav.addObject(message);
		return mav;
	}
}
