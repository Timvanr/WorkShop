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

	public Artikel getArtikelWithArtikelId(int artikel_id) throws SQLException{
		Connection connection = getConnection();
		String getArtikelString = "SELECT * FROM artikel WHERE artikel_id=?;";
		RowSet rs = null;
		Artikel artikel = new Artikel();
		try{

			getConnection();
			rs = new JdbcRowSetImpl(connection);
			rs.setCommand(getArtikelString);
			rs.setInt(1, artikel_id);
			rs.execute();
			ResultSetMetaData rowSetMD = rs.getMetaData();

			if (!rs.isBeforeFirst()) {
				System.out.println("There are no records with this artikel_id");
				return artikel;
			}

			for (int i = 1; i <= rowSetMD.getColumnCount(); i++) {
				System.out.printf("%-12s\t", rowSetMD.getColumnLabel(i));
			}
			System.out.println();

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.printf("%-12s\t", rs.getObject(i));
					if (i % rowSetMD.getColumnCount() == 0) {
						System.out.println();
					}
					artikel.setId(rs.getInt(1));
					artikel.setNaam(rs.getString(2));
					artikel.setPrijs(rs.getBigDecimal(3));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println();
			rs.close();
		}
		return artikel;	
	}

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
}
