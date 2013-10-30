
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
    	    this.id = SQL.getID("idAdministrator", "Administrator");
    	}
    	else {
    		this.id = SQL.getID("idCustomer", "Customer");
    	}
    	this.firstName = firstName;
    	this.secondName = secondName;
    	this.passwort = passwort;
    	this.loggedIn = false;
    	
    	String[] value = new String[4];
    	value[0] = "" + id;
    	value[1] = firstName;
    	value[2] = secondName;
    	value[3] = passwort;  // ToDo: Verschlüsseln
    	if (admin)
    	    SQL.insert(value, "Administrator");
    	else
    		SQL.insert(value, "Customer");
    }
    
    public Person(int id, boolean admin) {
    	
    	if (admin) {
    	    String[] column = {"idAdministrator", "firstNameAdministrator", "secondNameAdministrator", "passwortAdministrator" };
            String[] condition = {"idAdministrator = " + id};
            String[][] value = SQL.select(column, "Administrator", condition, "and");

            this.id = id;
    		this.firstName = value[0][1];
    		this.secondName = value[0][2];
    		this.passwort = value[0][3];
        	this.loggedIn = false;
        	this.accounts = new ArrayList<Account>();
    	}
    	else {
        	String[] column = {"idCustomer", "firstNameCustomer", "secondNameCustomer", "passwortCustomer" };
            String[] condition = {"idCustomer = " + id};
            String[][] value = SQL.select(column, "Customer", condition, "and");

        	this.id = id;
        	this.firstName = value[0][1];
        	this.secondName = value[0][2];
        	this.passwort = value[0][3];
        	this.loggedIn = false;
        	this.accounts = new ArrayList<Account>();    		
    	}
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
