package classes;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Person {
    protected int id;
    protected String firstName;
    protected String secondName;
    protected String password;
    protected boolean loggedIn;
    protected ArrayList<Account> accounts;
    
    public Person(String firstName, String secondName, String password, boolean admin) throws SQLException {
    	if (admin) {
    	    this.id = SQL.getID("idAdministrator", "Administrator");
    	}
    	else {
    		this.id = SQL.getID("idCustomer", "Customer");
    	}
    	this.firstName = firstName;
    	this.secondName = secondName;
    	this.password = password;
    	this.loggedIn = false;
    	
    	String[] value = new String[4];
    	value[0] = "" + id;
    	value[1] = firstName;
    	value[2] = secondName;
    	value[3] = password;  // ToDo: Verschlüsseln
    	if (admin)
    	    SQL.insert(value, "Administrator");
    	else
    		SQL.insert(value, "Customer");
    }
    
    public Person(int id, boolean admin) throws SQLException {
    	
    	if (admin) {
    	    String[] column = {"firstNameAdministrator", "secondNameAdministrator", "passwordAdministrator" };
            String[] condition = {"idAdministrator = " + id};
            String[][] value = SQL.select(column, "Administrator", condition, "and");
            
            if (value.length == 0)
            	return;
            this.id = id;
    		this.firstName = value[0][0];
    		this.secondName = value[0][1];
    		this.password = value[0][2];
        	this.loggedIn = false;
    	}
    	else {
        	String[] column = {"firstNameCustomer", "secondNameCustomer", "passwordCustomer" };
            String[] condition = {"idCustomer = " + id};
            String[][] value = SQL.select(column, "Customer", condition, "and");

        	this.id = id;
        	this.firstName = value[0][0];
        	this.secondName = value[0][1];
        	this.password = value[0][2];
        	this.loggedIn = false;   		
    	}
    }
    
    public Person() {
    	
    }
    
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
