
import java.lang.reflect.Field;

public class BuildStatement {
	public <E> String buildInsertStatement(E object) throws MissingEntityException {
		String buildSqlStatement;
		String valueFieldEnd;

		if (object.getClass().isAnnotationPresent(Entity.class)) {
			int variableToInsert = 0;
			String sqlTableName = object.getClass().getSimpleName().toUpperCase();
			buildSqlStatement = "INSERT INTO " + sqlTableName + " (";
			valueFieldEnd = "values (";
			Field[] declaredFields = object.getClass().getDeclaredFields(); // Array
																			// van
																			// velden
																			// die
																			// gedeclareerd
																			// zijn
																			// wordt
																			// geinitialiseerd
			for (int i = 0; i < declaredFields.length; i++) { // Gaat voor ieder
																// veld uit de
																// POJO na...

				try {
					declaredFields[i].setAccessible(true); // Verplicht als
															// velden private
															// zijn

					if (declaredFields[i].get(object) != null) { // Als het
																	// object
																	// uit veld
																	// x niet
																	// null
																	// (String
																	// of
																	// object)
																	// is

						if (!isPrimitiveZero(declaredFields[i].get(object))) { // En
																				// als
																				// de
																				// primitieve
																				// waarde
																				// ook
																				// geen
																				// nul/0
																				// is
																				// (zie
																				// methode
																				// onder)
							variableToInsert++; // neem +1 variabele op in het
												// insertstatement
							if (variableToInsert > 1) { // zijn er meer dan 1
														// variabelen voeg dan
														// komma's toe tussen
														// variabelen
								buildSqlStatement += ", ";
								valueFieldEnd += ", ";
							}

							buildSqlStatement += declaredFields[i].getName(); // voeg
																				// aan
																				// bSQLstmt
																				// de
																				// naam
																				// van
																				// het
																				// veld
																				// toe
							if (declaredFields[i].get(object) instanceof String) {
								valueFieldEnd += "\'"; // Als het veld een
														// string bevat voeg dan
														// ' ' toe (voorkomt
														// problemen als string
														// een getal is)
							}
							valueFieldEnd += declaredFields[i].get(object);
							if (declaredFields[i].get(object) instanceof String) {
								valueFieldEnd += "\'";
							}
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException | SecurityException ex) {
					ex.printStackTrace();
				}

			}
		} else {
			throw new MissingEntityException();
		}
		return buildSqlStatement + ") " + valueFieldEnd + ")";
	}

	public boolean isPrimitiveZero(Object object) {
		boolean isPrimitiveZero = false;

		if (object instanceof Long) {
			if ((Long) object == 0) {
				isPrimitiveZero = true;
			}
		} else if (object instanceof Integer) {
			if ((Integer) object == 0) {
				isPrimitiveZero = true;
			}
		}

		else if (object instanceof Float) {
			if ((Float) object == 0) {
				isPrimitiveZero = true;
			}
		} else if (object instanceof Double) {
			if ((Double) object == 0) {
				isPrimitiveZero = true;
			}
		}
		return isPrimitiveZero;
	}

	public <E> String createTable(E object)
			throws MissingEntityException, MissingIdException, InvalidDataTypeException {

		String buildSqlStatement; // Declaratie van Strings die gebruikt gaan worden
		String columnNameSql;
		String tabelNaam;
		String primaryKey;

		if (object.getClass().isAnnotationPresent(Entity.class)) { // Als de
																	// Entity
																	// Annotatie
																	// aanwezig
																	// is
																	// vervolg
																	// Anders
																	// gooi
																	// exception
																	// (zie
																	// onder)
																	// op.
			buildSqlStatement = "CREATE TABLE ";
			tabelNaam = "";
			columnNameSql = "";
			primaryKey = "";
			String primaryKeyValue = "";

			if (object.getClass().isAnnotationPresent(Table.class)) {
				Table table = object.getClass().getAnnotation(Table.class);
				tabelNaam = table.value() + " (";

			} else {
				tabelNaam = object.getClass().getSimpleName().toUpperCase() + " (";
			}

			Field[] declaredFields = object.getClass().getDeclaredFields(); // Haalt alle gedeclareerde velden op
			boolean hasPrimaryKeyField = false; // Dit is gebruikt omdat nagegaan moet worden of er
												// een primary-key veld in de klasse gedefinieerd is.

			for (Field field : declaredFields) { // Loop gaat na of 1 van de gedeclareerde velden de
													// primary key annotatie bevat
				if (field.isAnnotationPresent(Id.class)) {
					Column column = field.getAnnotation(Column.class);
					primaryKeyValue = column.kolomNaam();
					primaryKey = "PRIMARY KEY (" + column.kolomNaam() + "))";
					hasPrimaryKeyField = true;
				}
			}
			if (hasPrimaryKeyField = true) { // als er inderdaad een primary-key is..
											// else (zie onder) throw MissingIdexception
				for (Field field : declaredFields) { // In deze loop worden de kolomnamen bepaald

					if (field.isAnnotationPresent(Column.class)) { // Als voor het veld de column annotatie aanwezig is...
						Column column = field.getAnnotation(Column.class);
						if (column.kolomNaam().equals(primaryKeyValue)) { // Als de kolom de Primary key kolom is..
																		  // moet NOT NULL AUTO_INCREMENT worden toegevoegd
							columnNameSql += column.kolomNaam() + " " + column.type() + " NOT NULL AUTO_INCREMENT, ";
						} else { // Anders alleen de kolomnaam met een komma
							columnNameSql += column.kolomNaam() + " " + column.type() + ", ";
						}
					} else { // als er geen Kolom annotaties gebruikt zijn neem dan de waarden van de namen van de velden

						try {
							field.setAccessible(true); // Maak private variabelen toegankelijk
							if (field.getType().isAssignableFrom(String.class)) { // is het veldType een string..
								columnNameSql += field.getName() + " VARCHAR(45)"; // Voeg naam en VARCHAR(45) toe
							} else if (field.getType().isAssignableFrom(Long.class)) {// is het veldType een long..
								columnNameSql += field.getName() + " LONG(11)"; // Voeg naam en LONG(11) toe
							} else if (field.getType().isAssignableFrom(int.class)) { // idem
								columnNameSql += field.getName() + " INT(11) ";
							} else {
								throw new InvalidDataTypeException();
							}
						} catch (IllegalArgumentException | SecurityException ex) {
							ex.printStackTrace();
						}
					}
				}
			} else {
				throw new MissingIdException();
			}
		} else {
			throw new MissingEntityException();
		}

		return buildSqlStatement + tabelNaam + columnNameSql + primaryKey + ") ";

	}

}