import java.util.ArrayList;

public class Administrator extends Person {

	public Administrator(String firstName, String secondName, String passwort) {
	    super(firstName, secondName, passwort, true); 
	}
	
	public Administrator(int id) {
		super(id, true);
	}
}
