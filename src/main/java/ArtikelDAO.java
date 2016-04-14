import java.sql.SQLException;

public interface ArtikelDAO {

	int createArtikel(Artikel artikel) throws SQLException;
	Artikel getArtikelWithArtikelId(int artikel_id) throws SQLException;
	void deleteArtikelWithArtikelId(int artikel_id) throws SQLException;
	

}
