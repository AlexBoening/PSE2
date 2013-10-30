import java.util.ArrayList;

public class Customer extends Person {

	public Customer(String firstName, String secondName, String passwort) {
	    super(firstName, secondName, passwort, false);
	}
	
	public Customer(int id) {
		super(id, false);
	}
}
