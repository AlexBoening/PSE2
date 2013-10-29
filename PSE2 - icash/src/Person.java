
import java.util.ArrayList;

public abstract class Person {
    private int id;
    private String firstName;
    private String secondName;
    private String passwort;
    private boolean loggedIn;
    private ArrayList<Account> accounts;
    
    public Person(String firstName, String secondName, String passwort, boolean admin) {
    	if (admin) {
    	    // ID für Admin generieren
    	}
    	else {
    		// ID für Customer generieren
    	}
    	this.firstName = firstName;
    	this.secondName = secondName;
    	this.passwort = passwort;
    }
    
    public void add(Account a) {
        accounts.add(a);	
    }
    
    public static void login(String firstName, String secondName) {
    	
    }
    
    public static void login(String firstName, String secondName, String passwort) {
    	
    }
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
