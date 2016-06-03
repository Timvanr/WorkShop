package com.workshop.main;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncrypt {

	public static void main(String[] args){
	/*
	 * dit gedeelte moet volgens mij in de dao komen bij account.save. originalpassword wordt 
	 * vervangen door de input van de gebruiker en dit wordt omgecodeerd en in de database gezet.
	 * 
	 * Normaal gesproken zou je dit met een Hasing algorithm kunnen doen en hier een zogenaamde salt
	 * code aan toe kunnen voegen. Als je alleen de hashing zou gebruiken zouden kwaadwillenden het
	 * wachtwoord alsnog kunnen achterhalen met een zgn dictionary. Een salt kan je zelf opstellen
	 * of laten genereren. Dit wordt als het ware toegevoegd aan het wachtwoord en daarna wordt het
	 * complete wachtwoord gehasht. Voor een voorbeeld kun je de links nog bekijken. De BCrypt methode
	 * doet eigenlijk alles in één dus dat is makkelijker in te voegen.
	 */
		String originalpassword = "password123";
	String generatedSecuredPasswordHash = BCrypt.hashpw(originalpassword, BCrypt.gensalt(12));
	System.out.println(generatedSecuredPasswordHash);
	/*
	 * met onderstaande methode kun je omgekeerd kijken of het opgeslagen wachtwoord 
	 * (generatedSecuredPasswordHash) overeenkomt met de inlog die de gebruiker bij het 
	 * inloggen opgeeft. In dit geval staat er nog originalpassword maar dit is nu slechts ter
	 * controle gedaan.
	 */
	boolean matched = BCrypt.checkpw(originalpassword, generatedSecuredPasswordHash);
	System.out.println(matched);
	}
	}