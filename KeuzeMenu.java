import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class KeuzeMenu {
	Scanner input;
    KlantDAO klantDAO;
    Klant klant;
    BestellingDAO bestellingDAO;
    
    static final KeuzeMenu keuzeMenu = new KeuzeMenu();
    
    public KeuzeMenu(){
        input = new Scanner(System.in);
        klantDAO = new KlantDAO();
        klant = new Klant();
        bestellingDAO = new BestellingDAO();
}
    
    public static void main(String[] args) {

    	keuzeMenu.startInlog();
    	
    }
    
    public void startInlog() {
        System.out.println("Inlog scherm: ");
        System.out.println("");
        System.out.print("Gebruikersnaam: ");
        String user = input.next();
        System.out.print("Wachtwoord: ");
        String pass = input.next();
        
        if ( DatabaseConnection.getConnection(user, pass) == null) {
        	System.out.println("Probeer een andere gebruikersnaam en/of wachtwoord.");
        }
        else
        	startHoofd();
                 
        }
    

	private void startHoofd() {
		 System.out.println("Hoofdmenu: ");
		 System.out.println("");
	     System.out.println("1. CRUD-handelingen");
	     System.out.println("2. Klasse-selectie");
	     System.out.println("3. Uitloggen");
	     System.out.println("4. Stoppen");
	     System.out.println("");
	     System.out.println("Maak een keuze: ");
	     int keuze = input.nextInt();
	     switch (keuze){
         case 1:
             startCrud();
             break;
         case 2:
             startClassSelectie();
             break;
         case 3:
             DatabaseConnection.setUsernameAndPassword(null, null);
             System.out.println("Succesvol uitgelogd!");
             startInlog();
             break;
         case 4:
             exit();
             break;
         default:
             System.out.println("Ongeldige keuze!");
             startHoofd();
             break;
     }
 }

	

	

	private void startCrud() {
		System.out.println("CRUD-handelingen");
		System.out.println("");
		System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5: Hoofdmenu");
        System.out.println("");
        System.out.println("Maak een keuze:");
        int keuze = input.nextInt();
	     switch (keuze){
	     case 1:
             createKlant();
             break;
         case 2:
             readKlant();     
             break;
         case 3:
             updateKlant();
             break;
         case 4:
             deleteKlant();
             break;
         case 5:
             System.out.println("Terug naar het hoofdmenu...");
             startHoofd();
         default:
             System.out.println("Ongeldige keuze!");
             startCrud();
             break;
     }
 }
	

	private void createKlant() {
		System.out.println("Maak een nieuwe klant aan: ");
		System.out.print("Voornaam: ");
		String voornaam = input.next();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.next();
		System.out.print("Achternaam: ");
		String achternaam = input.next();
		System.out.println("Emailadres: ");
		String email = input.next();
		klant.setVoornaam(voornaam);
		klant.setTussenvoegsel(tussenvoegsel);
		klant.setAchternaam(achternaam);
		klant.setEmail(email);

	}
	
	private void startClassSelectie() {
		// TODO Auto-generated method stub
		
	}
	private void exit() {
		// TODO Auto-generated method stub
		
	}
	

private void updateKlant() {
	// TODO Auto-generated method stub
	
}

private void deleteKlant() {
	// TODO Auto-generated method stub
	
}

private void readKlant() {
	// TODO Auto-generated method stub
	
}
}
	