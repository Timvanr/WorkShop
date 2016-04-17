import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.RowSet;
import com.mysql.jdbc.Statement;
import com.sun.rowset.JdbcRowSetImpl;

public class ArtikelLijstDAOFireBird implements ArtikelDAO {

	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
		return connection;		
	}

	public ArtikelLijstDAOFireBird(){
	}

	@Override
	public int createArtikel(Artikel artikel) throws SQLException{
		Connection connection = getConnection();
		String createArtikelString = "INSERT INTO artikel (artikel_naam, artikel_prijs) values (?,?);";
		PreparedStatement createArtikel = null;
		int artikel_id = 0;
		try{
			getConnection();
			createArtikel = connection.prepareStatement(createArtikelString); 
			createArtikel.setString(1, artikel.getNaam());
			createArtikel.setBigDecimal(2, artikel.getPrijs());
			ResultSet resultSet = createArtikel.executeQuery();
			while (resultSet .next()) {
				artikel_id = resultSet.getInt("artikel_id");
				System.out.println("Artikel met artikelid " + artikel_id + " is succesvol aangemaakt");
			}			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			createArtikel.close();
		}
		return artikel_id;
	}
	@Override
	public Artikel getArtikelWithArtikelId(int artikel_id) throws SQLException{
		String getArtikelString = "SELECT * FROM artikel WHERE artikel_id=?;";
		RowSet rs = null;
		Artikel artikel = null;
		try{
			
			Connection connection = getConnection();
			rs = new JdbcRowSetImpl(connection);
			rs.setCommand(getArtikelString);
			rs.setInt(1, artikel_id);
			rs.execute();
			
			
			if (!rs.isBeforeFirst()) {
				System.out.println("There are no records with this artikel_id");
				return artikel;
			}

			while (rs.next()) {
				artikel = new Artikel();
				artikel.setId(rs.getInt("artikel_id"));
				artikel.setNaam(rs.getString("artikel_naam"));
				artikel.setPrijs(rs.getBigDecimal("artikel_prijs"));
		
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
		}
		return artikel;
		
	}

	@Override
	public void deleteArtikelWithArtikelId(int artikel_id) throws SQLException{
		Connection connection = getConnection();
		String deleteArtikelString = "DELETE * FROM artikel WHERE artikel_id=?;";
		PreparedStatement deleteArtikel = connection.prepareStatement(deleteArtikelString);

		try{
			deleteArtikel.setInt(1, artikel_id);
			deleteArtikel.executeUpdate();
			System.out.println("Artikel met artikel_id " + artikel_id + " is succesvol verwijderd");
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			deleteArtikel.close();
		}
	}

	@Override
	public void voegArtikelToeAanBestelling(int bestelling_id, int artikel_id, int artikel_aantal) throws SQLException{
		Connection connection = getConnection();
		try{
			PreparedStatement voegArtikelToe = connection.prepareStatement(
					"INSERT INTO bestelling_has_artikel (artikel_id, bestelling_id, artikel_aantal) values (?, ?, ?)");
		voegArtikelToe.setInt(1, artikel_id);
		voegArtikelToe.setInt(2, bestelling_id);
		voegArtikelToe.setInt(3, artikel_aantal);
		voegArtikelToe.executeUpdate();
		System.out.println("Artikel toegevoegd aan bestelling");
	}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			connection.close();
		}
		}

	@Override
	public void verwijderArtikelUitBestelling(int bestelling_id, int artikel_id) throws SQLException{
		Connection connection = getConnection();
		try{
			PreparedStatement verwijderArtikel = connection.prepareStatement(
					"DELETE FROM bestelling_has_artikel WHERE artikel_id=? AND bestelling_id=?");
		verwijderArtikel.setInt(1, artikel_id);
		verwijderArtikel.setInt(2, bestelling_id);
		verwijderArtikel.executeUpdate();
		System.out.println("Artikel verwijderd uit bestelling");
	}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			connection.close();
		}
		}
}
