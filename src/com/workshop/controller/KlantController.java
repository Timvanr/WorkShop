package com.workshop.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.service.KlantService;



@Controller
public class KlantController {

	@Autowired
	public KlantService klantService;
	
	@RequestMapping ("/welcome")
    public ModelAndView listKlanten() {
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		return new ModelAndView("welcome", "message", message);
		
    }
}