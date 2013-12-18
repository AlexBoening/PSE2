package classes;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * abstract class Person
 * 
 */
public abstract class Person {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected boolean loggedIn;
    protected ArrayList<Account> accounts;
    
    /**
     * overloaded constructor for Person
     * @param firstName String
     * @param lastName String
     * @param password String
     * @param admin boolean if admin or not
     * @throws SQLException
     */
    public Person(String firstName, String lastName, String password, boolean admin) throws SQLException {
    	if (admin) {
    	    this.id = SQL.getID("idAdministrator", "Administrator", "");
    	}
    	else {
    		this.id = SQL.getID("idCustomer", "Customer", "");
    	}
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.password = password;
    	this.loggedIn = false;
    	
    	String[] value = new String[4];
    	value[0] = "" + id;
    	value[1] = firstName;
    	value[2] = lastName;
    	value[3] = password;  // ToDo: encrypt
    	if (admin)
    	    SQL.insert(value, "Administrator");
    	else
    		SQL.insert(value, "Customer");
    }
    
    /**
     * overloaded constructor for Person
     * @param id int ID
     * @param admin boolean if admin or not
     * @throws SQLException
     */
    public Person(int id, boolean admin) throws SQLException {
    	
    	if (admin) {
    	    String[] column = {"firstNameAdministrator", "lastNameAdministrator", "passwordAdministrator" };
            String[] condition = {"idAdministrator = " + id};
            String[][] value = SQL.select(column, "Administrator", condition, "and");
            
            if (value.length == 0)
            	return;
            this.id = id;
    		this.firstName = value[0][0];
    		this.lastName = value[0][1];
    		this.password = value[0][2];
        	this.loggedIn = false;
    	}
    	else {
        	String[] column = {"firstNameCustomer", "lastNameCustomer", "passwordCustomer" };
            String[] condition = {"idCustomer = " + id};
            String[][] value = SQL.select(column, "Customer", condition, "and");

        	this.id = id;
        	this.firstName = value[0][0];
        	this.lastName = value[0][1];
        	this.password = value[0][2];
        	this.loggedIn = false;   		
    	}
    }
    
    /**
     * Empty Constructor for Clients without Database Connection
     */
    public Person() {
    	
    }
    
    /**
     * adds an Account to the object
     * @param a Account
     */
    public void add(Account a) {
    	if (accounts == null)
    		accounts = new ArrayList<Account>();
        accounts.add(a);	
    }
    
    public void login() {
    	setLoggedIn(true);
    }
    
    public int getId() {
    	return this.id;
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
