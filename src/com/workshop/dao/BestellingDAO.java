package workshop.dao.mysql;

import java.sql.*;

import workshop.DatabaseConnection;
import workshop.model.*;
import workshop.dao.BestellingDAOInterface;

import com.sun.rowset.*;
import java.util.*;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BestellingDAO implements workshop.dao.BestellingDAOInterface {
	
	private EntityManager em;
	
	@Override
	public long createBestelling(Bestelling bestelling) {
		this.em.persist(bestelling);
		
		return bestelling.getId();
	}
	
	@Override
	public Bestelling getBestelling(long id) {
		return this.em.find(Bestelling.class, id);
	}
	
	@Override
	public long haalKlant_id(long id) {
		return getBestelling(id).getKlant_id();
	}
	
	public void verwijderBestelling(long id) {
		this.em.remove(getBestelling(id).getClass());
	}
	
	@Override
	public void updateBestelling(long id, Bestelling bestelling) {
		Bestelling oud = getBestelling(id);
		
		this.em.getTransaction().begin();
		oud.setKlant(bestelling.getKlant());
		oud.setArtikelen(bestelling.getArtikelen());
		oud.setDatum(bestelling.getDatum());
		oud.setFactuur(bestelling.getFactuur());
		this.em.getTransaction().commit();
	}
	
	@Override
	public List<Bestelling> haalBestellijst() {
		return this.em.createQuery("SELECT * FROM ", Bestelling.class).getResultList();
	}
	
	@Override
	public List<Bestelling> getBestellijstByKlant(long klant_id) {
		return this.em.createQuery
				("SELECT * FROM " + Bestelling.class.getSimpleName() + "WHERE klant_id = " + klant_id).getResultList();
	}
	
/**	
 * * Oude Meuk Hiero
 *
	private static Connection getConnection(){
		return DatabaseConnection.getPooledConnection();
	}
/*	
	@Override
	public void createBestelling(Bestelling bestelling){
		connection = getConnection();
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			PreparedStatement voegToe = connection.prepareStatement
					("INSERT INTO Bestelling (klant_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			voegToe.setLong(1, bestelling.getKlant_id());
			//voegToe.setDate(2, new Date());
			voegToe.executeUpdate();
			
			ResultSet rs = voegToe.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				bestelling.setBestelling_id(rs.getInt(1));
			}
			voegToe.close();
			
			for (Map.Entry<Artikel, Integer> entrySet: artikelen.entrySet()) {
				PreparedStatement insertArtikelen = connection.prepareStatement(
						"INSERT INTO bestelling_has_artikel " +
						"(bestelling_id, artikel_id, artikel_aantal) " +
						"VALUES (?, ?, ?)");
				insertArtikelen.setInt(1, bestelling.getBestelling_id());
				insertArtikelen.setInt(2, entrySet.getKey().getId());
				insertArtikelen.setInt(3, entrySet.getValue());
				insertArtikelen.executeUpdate();
				insertArtikelen.close();
			}
						
			System.out.println("Bestelling added!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
	}

	
	@Override
	public Bestelling getBestelling(int bestelling_id){
		connection = getConnection();
		Bestelling bestelling = null;
		ArtikelDAO artikellijst = new ArtikelDAO();
		try {
			bestelling = new Bestelling();
			RowSet bestelData = new JdbcRowSetImpl(connection);
			bestelData.setCommand(
					"SELECT * FROM Bestelling " +
					"WHERE bestelling_id = ?");
			bestelData.setInt(1, bestelling_id);
			bestelData.execute();
			
			if (bestelData.isBeforeFirst()){
				bestelData.next();
				bestelling.setKlant_id(bestelData.getInt("klant_id"));
				bestelling.setBestelling_id(bestelling_id);
				
				bestelData.setCommand(
						"SELECT * FROM bestelling_has_artikel " +
						"WHERE bestelling_id = ?");
				bestelData.setInt(1, bestelling_id);
				bestelData.execute();
				
				if (bestelData.isBeforeFirst()){
					while (bestelData.next()){
						bestelling.voegArtikelToe
								(artikellijst.getArtikelWithArtikelId(bestelData.getInt("artikel_id")), bestelData.getInt("artikel_aantal"));	
					}
				} else {
					System.out.println("Deze bestelling is leeg; geen artikelen gevonden");
				}
			} else {
				System.out.println("Fout bij het lezen van de bestelling of geen bestelling gevonden");
			}
			
			bestelData.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return bestelling;
	}
	
	@Override
	public Set<Bestelling> haalBestellijst(){
		connection = getConnection();
		RowSet rowSet = null;
		Set<Bestelling> bestellijst = new LinkedHashSet();
		try {
			rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand("SELECT * FROM Bestelling");
			rowSet.execute();

			while (rowSet.next()) {
				bestellijst.add(getBestelling(rowSet.getInt(1)));
			}
			rowSet.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return bestellijst;
	}

	@Override
	public void verwijderBestelling(int bestelling_id){
		connection = getConnection();
		PreparedStatement verwijder = null;
		PreparedStatement removeArtikel = null;
		try {
			verwijder = connection.prepareStatement(
					"DELETE FROM Bestelling " + 
					"WHERE bestelling_id = ?");
			verwijder.setInt(1, bestelling_id);
			removeArtikel = connection.prepareStatement(
					"DELETE FROM bestelling_has_artikel " +
					"WHERE bestelling_id = ?");
			removeArtikel.setInt(1, bestelling_id);
			removeArtikel.executeUpdate();
			verwijder.executeUpdate();
			
			removeArtikel.close();
			verwijder.close();
			System.out.println("Bestelling " + bestelling_id + " is verwijderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public int haalKlant_id(int bestelling_id){
		PreparedStatement haalKlant_id = null;
		int klant_id = 0;
		try {
			connection = getConnection();
			haalKlant_id = connection.prepareStatement
					("SELECT klant_id FROM Bestelling WHERE bestelling_id = ?");
			haalKlant_id.setInt(1, bestelling_id);
			ResultSet rs = haalKlant_id.executeQuery();

			if (rs.next()) {
				klant_id = rs.getInt(1);
			} else {
				System.out.println("Bestelling niet gevonden; kan geen bijbehorende klant retourneren");
			}
			haalKlant_id.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return klant_id;
	}
	
	@Override
	public Set<Bestelling> getBestellijstByKlant(int klant_id){
		connection = getConnection();
		
		Set<Bestelling> bestellijst = new LinkedHashSet();		
		try {
			RowSet bestellijstByKlant = new JdbcRowSetImpl(connection);
			bestellijstByKlant.setCommand(
					"SELECT * FROM Bestelling " +
					"WHERE klant_id = ?");
			bestellijstByKlant.setInt(1, klant_id);
			bestellijstByKlant.execute();
			
			while (bestellijstByKlant.next()){
				bestellijst.add(getBestelling(bestellijstByKlant.getInt(1)));
			}
			
			bestellijstByKlant.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return bestellijst;
	}
	
	@Override
	public void updateBestelling(int bestelling_id, Bestelling bestelling) {
		PreparedStatement update = null;
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			connection = getConnection();
			
			PreparedStatement deleteArtikelenOud = connection.prepareStatement(
					"DELETE FROM bestelling_has_artikel " +
					"WHERE bestelling_id = ?");
			deleteArtikelenOud.setInt(1, bestelling_id);
			deleteArtikelenOud.executeUpdate();
			deleteArtikelenOud.close();
			
			for (Map.Entry<Artikel, Integer> entrySet: artikelen.entrySet()){
				update = connection.prepareStatement(
						"INSERT INTO bestelling_has_artikel " +
						"(bestelling_id, artikel_id, artikel_aantal) " +
						"VALUES (?, ?, ?)");
				update.setInt(1, bestelling_id);
				update.setInt(2, entrySet.getKey().getId());
				update.setInt(3, entrySet.getValue());
				update.executeUpdate();
				update.close();
			}
			System.out.println("Bestelling " + bestelling_id + " is veranderd!");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
	}

	
	/*
	public void verwijderTabel() throws SQLException {
		connection = DatabaseConnection.getPooledConnection();

		Statement dropTable = connection.createStatement();
		dropTable.executeUpdate("DROP TABLE Bestelling");

		System.out.println("table Bestelling dropped!");
	}
	
	public void close(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed!");
	}
*/
	
}
