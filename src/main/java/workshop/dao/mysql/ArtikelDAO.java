package workshop.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import workshop.DatabaseConnection;
import workshop.model.Artikel;
import javax.sql.RowSet;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Statement;
import com.sun.rowset.JdbcRowSetImpl;
@Repository
public class ArtikelDAO implements workshop.dao.ArtikelDAOInterface {

	@Autowired
	SessionFactory sessionFactory;
	private static Connection getConnection() {
		Connection connection = DatabaseConnection.getPooledConnection();

		return connection;
	}

	public ArtikelDAO() {

	}

	public ArtikelDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public int createArtikel(Artikel artikel) {
		Connection connection = getConnection();
		String createArtikelString = "INSERT INTO Artikel (artikel_naam, artikel_prijs) values (?,?);";
		PreparedStatement createArtikel = null;
		ResultSet rs = null;
		int artikel_id = 0;
		try {
			createArtikel = connection.prepareStatement(createArtikelString, java.sql.Statement.RETURN_GENERATED_KEYS); 
			createArtikel.setString(1, artikel.getNaam());
			createArtikel.setBigDecimal(2, artikel.getPrijs());
			createArtikel.executeUpdate();

			rs = createArtikel.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				artikel_id = rs.getInt(1);
				System.out.println("Artikel met artikel_id " + artikel_id + " succesvol aangemaakt");
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
				createArtikel.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return artikel_id;
	}

	@Override
	@Transactional
	public Artikel getArtikelWithArtikelId(int artikel_id) {
		Connection connection = getConnection();
		String getArtikelString = "SELECT * FROM Artikel WHERE artikel_id=?;";
		RowSet rs = null;
		Artikel artikel = null;
		try {
			rs = new JdbcRowSetImpl(connection);
			rs.setCommand(getArtikelString);
			rs.setInt(1, artikel_id);
			rs.execute();

			if (rs.isBeforeFirst()) {

				while (rs.next()) {
					artikel = new Artikel();
					artikel.setId(rs.getInt("artikel_id"));
					artikel.setNaam(rs.getString("artikel_naam"));
					artikel.setPrijs(rs.getBigDecimal("artikel_prijs"));

				}
			} else {
				System.out.println("geen artikel met dit artikelnummer gevonden");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return artikel;
	}
	
	@Override
	@Transactional
	public Artikel getArtikelWithNaam(String naam) {
		Connection connection = getConnection();
		String query = "SELECT * FROM Artikel WHERE artikel_naam = ?";
		Artikel artikel = null;
		RowSet artikelByNaam = null;
		try {
			artikelByNaam = new JdbcRowSetImpl(connection);
			artikelByNaam.setCommand(query);
			artikelByNaam.setString(1, naam);
			artikelByNaam.execute();
			
			if (artikelByNaam.next()){
				artikel = new Artikel();
				artikel.setId(artikelByNaam.getInt("artikel_id"));
				artikel.setNaam(naam);
				artikel.setPrijs(artikelByNaam.getBigDecimal("artikel_prijs"));
			} else {
				System.out.println("Artikel niet gevonden");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				artikelByNaam.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return artikel;
	}
	
	
	@Override
	@Transactional
	public Set<Artikel> getArtikellijst(){
		Connection connection = getConnection();
		String query = "SELECT * FROM Artikel ORDER BY artikel_naam";
		RowSet allArtikelen = null;
		Set<Artikel> artikelen = new LinkedHashSet();
		try {
			allArtikelen = new JdbcRowSetImpl(connection);
			allArtikelen.setCommand(query);
			allArtikelen.execute();
			
			while (allArtikelen.next()){
				Artikel a = new Artikel();
				a.setId(allArtikelen.getInt("artikel_id"));
				a.setNaam(allArtikelen.getString("artikel_naam"));
				a.setPrijs(allArtikelen.getBigDecimal("artikel_prijs"));
				artikelen.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				allArtikelen.close(); 
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return artikelen;
	}
	
	@Override
	@Transactional
	public void updateArtikel(int id, Artikel artikel) {
		Connection connection = getConnection();
		String update = "UPDATE Artikel SET artikel_naam = ?, artikel_prijs = ? WHERE artikel_id = ?";
		PreparedStatement updateArtikel = null;
		try {
			updateArtikel = connection.prepareStatement(update);
			updateArtikel.setString(1, artikel.getNaam());
			updateArtikel.setBigDecimal(2, artikel.getPrijs());
			updateArtikel.setInt(3, id);
			updateArtikel.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				updateArtikel.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	@Transactional
	public void deleteArtikelWithArtikelId(int artikel_id) {
		Connection connection = getConnection();
		String deleteArtikelString = "DELETE FROM Artikel WHERE artikel_id=?;";
		PreparedStatement deleteArtikel = null;
		try {
			deleteArtikel = connection.prepareStatement(deleteArtikelString);
			deleteArtikel.setInt(1, artikel_id);
			deleteArtikel.executeUpdate();
			System.out.println("Artikel met artikel_id " + artikel_id + " is succesvol verwijderd");
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				deleteArtikel.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
/*
	@Override
	public void verwijderArtikelUitBestelling(int bestelling_id, int artikel_id) throws SQLException {
		Connection connection = getConnection();
		try {
			PreparedStatement verwijderArtikel = connection
					.prepareStatement("DELETE FROM bestelling_has_artikel WHERE artikel_id=? AND bestelling_id=?");
			verwijderArtikel.setInt(1, artikel_id);
			verwijderArtikel.setInt(2, bestelling_id);
			verwijderArtikel.executeUpdate();
			System.out.println("Artikel verwijderd uit bestelling");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			connection.close();
		}
	}

	@Override
	public void voegArtikelToeAanBestelling(int bestelling_id, int artikel_id, int artikel_aantal) throws SQLException {
		Connection connection = getConnection();
		try {
			PreparedStatement voegArtikelToe = connection
					.prepareStatement("INSERT INTO bestelling_has_artikel (artikel_id, bestelling_id, artikel_aantal) values (?, ?, ?)");
			voegArtikelToe.setInt(1, artikel_id);
			voegArtikelToe.setInt(2, bestelling_id);
			voegArtikelToe.setInt(3, artikel_aantal);
			voegArtikelToe.executeUpdate();
			System.out.println("Artikel toegevoegd aan bestelling");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/*
	 * public void close() { try { connection.close(); } catch (SQLException e)
	 * { e.printStackTrace(); } }
	 */

}
