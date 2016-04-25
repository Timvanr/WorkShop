package workshop.misc;

public class InvalidDataTypeException extends Exception{

	public InvalidDataTypeException(){
		super("De database kan alleen de volgende datatypen bevatten: String, Long, Int");
	}
}
