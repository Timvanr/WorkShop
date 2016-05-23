package workshop.controller;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import workshop.dao.mysql.KlantDAO;
import workshop.model.Klant;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.RequestDispatcher;

@WebServlet("/KlantController")
public class KlantController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/nieuw.jsp";
    private static String LIST_KLANT = "/nieuw.jsp";
    private KlantDAO dao = new KlantDAO();
    
    //@RequestMapping(method=RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
        	Long klant_id = Long.parseLong(request.getParameter("idKlant"));
            dao.delete(dao.findOne(klant_id));
            forward = LIST_KLANT;
            request.setAttribute("klanten", dao.findAll());    
        } else if (action.equalsIgnoreCase("update")){
            //forward = INSERT_OR_EDIT;
            Long klant_id = Long.parseLong(request.getParameter("idKlant"));
            Klant klant = dao.findOne(klant_id);
            request.setAttribute("klant", klant);
        } else if (action.equalsIgnoreCase("listKlant")){
            forward = LIST_KLANT;
            request.setAttribute("klanten", dao.findAll());
        } else {
        	System.out.println("geen idee of en waar dit geprint gaat worden");
        	/*Klant klant = new Klant();
            klant.setVoornaam(request.getParameter("voornaam"));
            klant.setTussenvoegsel(request.getParameter("tussenvoegsel"));
            klant.setAchternaam(request.getParameter("achternaam"));
            klant.setEmail(request.getParameter("email"));
            //String idKlant = request.getParameter("idKlant");
            dao.createKlant(klant);
            //forward = INSERT_OR_EDIT;*/
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	Klant klant = new Klant();
        klant.setVoornaam(request.getParameter("voornaam"));
        klant.setTussenvoegsel(request.getParameter("tussenvoegsel"));
        klant.setAchternaam(request.getParameter("achternaam"));
        klant.setEmail(request.getParameter("email"));
        String idKlant = request.getParameter("idKlant");
        dao.createKlant(klant);
  /*      if(idKlant == null || idKlant.isEmpty())
        {
            dao.createKlant(klant);
        }
        else
        {
            klant.setIdKlant(Integer.parseInt(idKlant));
            dao.updateKlant(klant);
        }*/
        RequestDispatcher view = request.getRequestDispatcher(LIST_KLANT);
        request.setAttribute("klanten", dao.findAll());
        view.forward(request, response);
    }
}
