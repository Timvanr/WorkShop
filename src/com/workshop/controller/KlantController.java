package com.workshop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workshop.model.*;
import com.workshop.service.AccountService;
import com.workshop.service.AdresService;
import com.workshop.service.KlantService;

@Controller
public class KlantController {
	
	@Autowired
	public KlantService klantService;
	@Autowired
	public AdresService adresService;
	@Autowired
	public AccountService accountService;
	
	public void toegang(){
		System.out.println("toegang tot controller?");
	}
	
	// Klant methode
	@RequestMapping ("Klant")
    public ModelAndView lijstKlanten() {
		ModelAndView mav = new ModelAndView("Klant");
		List<Klant> klanten = klantService.listKlanten();
		mav.addObject("klanten", klanten);
		return mav;
    }
	
	@RequestMapping ("CheckDatabase")
    public ModelAndView listKlanten() {
		ModelAndView mav = new ModelAndView("CheckDatabase");
		List<Klant> klanten = klantService.listKlanten();
		mav.addObject("klanten", klanten);
		return mav;
    }
		
	@RequestMapping(value = { "/deleteKlant{id}" }, method = RequestMethod.GET)
    public String deleteKlant(@PathVariable long id){
		this.klantService.deleteKlant(id);
        return "redirect:/Klant.html";
    }
	/*
	@RequestMapping(value = { "Klant/save" }, method = RequestMethod.POST)
	public String addKlant(@ModelAttribute("klant") Klant k){		
		this.klantService.addKlant(k);			
		return "redirect:/Klant.html";
	}*/
	
	@RequestMapping(value = {"findKlant"}, method = RequestMethod.GET)
	public ModelAndView findKlant(@RequestParam Map<String, String> requestParams) {
		String account_naam = requestParams.get("naam");
		String wachtwoord = requestParams.get("wachtwoord");
		ModelAndView mav = null;
		Account account = accountService.findByNaam(account_naam);
		if (account == null) {
			String acountNietGevonden = "Account niet gevonden";
			mav = new ModelAndView("Account");
			mav.addObject("accBericht", acountNietGevonden);
		}//BCrypt.checkpw(account.getWachtwoord(), wachtwoord)
		else if (BCrypt.checkpw(wachtwoord, account.getWachtwoord()) == false) {
			String verkeerdPW = "Het opgegeven wachtwoord voor deze account klopt niet";
			mav = new ModelAndView("Account");
			mav.addObject("accBericht", verkeerdPW);
		}
		else {
			Klant klant = klantService.findKlant(account.getKlant().getId());
			mav = new ModelAndView("findKlant");
			mav.addObject("klant", klant);
		}	
		return mav;
	}
/*
	@RequestMapping(value = { "Klant/saveAdres" }, method = RequestMethod.POST)
	public String addKlant(@ModelAttribute("klant, adres, adresType")
				Klant k, Adres a, AdresType at){
		this.adresService.addAdres(a);
		Map<Adres, AdresType> adressen = new HashMap<>();
		AdresType adresType = new AdresType();
		adresType.setAdres_type_id(1);
		adressen.put(a, adresType);
		k.setAdressen(adressen);
		this.klantService.addKlant(k);			
		return "redirect:/Klant.html";
	}*/
	
	//Account Methode
	@RequestMapping(value = { "Klant/NewAccount" }, method = RequestMethod.POST)
	public String addAccount(@ModelAttribute("klant, adres, account")
				Klant k, Adres a, Account ac){		
		ac.setWachtwoord(BCrypt.hashpw(ac.getWachtwoord(), BCrypt.gensalt(12)));
		try { 
			this.klantService.createAccount(k, a, ac);		
		}catch (Exception ex) {
			return "/Fout";
		}
		return "/NewAccount";
	}
	
	@RequestMapping ("/Account")
    public ModelAndView listAccounts() {
		ModelAndView mav = new ModelAndView("Account");
		List<Account> accounts = accountService.listAccounts();
		mav.addObject("accounts", accounts);
		return mav;
    }
	/*
	@RequestMapping(value = {"Account/findAccount"}, method =RequestMethod.GET)
	public ModelAndView findAccount(@RequestParam("id") String account_id) {
		ModelAndView mav =  new ModelAndView("findAccount");
		Long id = Long.decode(account_id);
		Account account = accountService.findAccount(id);
		Klant klant = klantService.findKlant(account.getKlant().getId());
		mav.addObject("account", account);
		mav.addObject("klant", klant);
		return mav;
	}*/
}