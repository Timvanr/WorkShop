public class MissingEntityException extends Exception{
	
	public MissingEntityException(){
		super("Geen Entity gedefinieerd voor deze klasse");
	}
}