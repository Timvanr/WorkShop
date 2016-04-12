import java.io.IOException;
import java.sql.SQLException;

public class BestellingController {
	
		private Service service; // view
		private BestellingDAO bestellijst; // model
		
		public BestellingController(Service service, BestellingDAO bestellijst2) {

			this.service = service;
			this.bestellijst = bestellijst2;
		}

		public void printBestellingmetId() throws SQLException, IOException{
			service.printBestelling(bestellijst.getBestelling(service.bestellingIdPrompt()));
		}

		public void printAlleBestellingen() throws SQLException{
			service.printBestellingen(bestellijst.haalBestellijst());
		}
		
		public void updateBestelling() throws SQLException, IOException {
			bestellijst.updateBestelling(bestellijst.getBestelling(service.bestellingIdPrompt()));
		}
		
		public void deleteBestelling() throws SQLException, IOException {
			bestellijst.verwijderBestelling(service.bestellingIdPrompt());
		}

}
