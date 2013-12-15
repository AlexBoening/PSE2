package classes;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Administrator class
 * extends Person
 */
public class Administrator extends Person {

	/**
	 * overloaded constructor for Administrator
	 * @param firstName String
	 * @param lastName String
	 * @param password String
	 * @throws SQLException
	 */
	public Administrator(String firstName, String lastName, String password) throws SQLException {
	    super(firstName, lastName, password, true); 
	}
	
	/**
	 * overloaded constructor for Administrator
	 * @param id
	 * @throws SQLException
	 */
	public Administrator(int id) throws SQLException {
		super(id, true);
	}
	
	/**
	 * constructor for Administrator
	 */
	public Administrator() {
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * deactivates given account
	 * @param account Account account to deactive
	 * @param active boolean true if active
	 * @throws SQLException
	 */
	public void deactivateAccount(Account account, boolean active) throws SQLException {
		if (account.getAdministrator().getId() == id)
	        account.setFlagActive(active);	
		else {
			// Error: you cannot deactivate Accounts you do not maintain!
		}
	}
	
	/**
	 * creates new Customer
	 * @param firstName String
	 * @param lastName String
	 * @param password String
	 * @throws SQLException
	 */
	public void createCustomer(String firstName, String lastName, String password) throws SQLException {
		Customer c = new Customer(firstName, lastName, password);
	}
	
	/**
	 * creates new Account
	 * @param flagActive boolean
	 * @param customer Customer
	 * @param bank Bank
	 * @param accountType AccountType
	 * @throws SQLException
	 */
	public void createAccount(boolean flagActive, Customer customer, Bank bank, AccountType accountType) throws SQLException {
		Account a = new Account (flagActive, customer, this, bank, accountType);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * gets all accounts managed by this administrator
	 * @return ArrayList<Account> list with accounts
	 * @throws SQLException
	 */
	public ArrayList<Account> getAccounts() throws SQLException {
		if (accounts == null) {
			accounts = new ArrayList<Account>();
			String[] column = {"idAccount"};
			String[] condition = {"Administrator_idAdministrator = " + id};
			String[][] value = SQL.select(column, "Account", condition, "and");
			for (int i=0; i<value.length; i++)
			    accounts.add(new Account(Convert.toInt(value[i][0])));
		}
		return accounts;
	}
	
	/**
	 * logs administrator in
	 * @param password
	 * @throws SQLException
	 */
    public void login(String password) throws SQLException{
    	String[] column = {"passwordAdministrator"};
    	String table = "Administrator";
    	String[] condition = {"idAdministrator = " + id};
    	String[][] value = SQL.select(column, table, condition, "and");
    	if (value.length > 0)
    		if (value[0][0].equals(password))
    			setLoggedIn(true);
    		else
    			setLoggedIn(false);
    	else
    		setLoggedIn(false);
    }
    
    /**
     * updates database
     * @throws SQLException
     */
	public void updateDB() throws SQLException {
		String[] condition = {"idAdministrator = " + id};
		String[] column = new String[3];
		String[] value = new String[3];
		
		column[0] = "firstNameAdministrator";
		column[1] = "lastNameAdministrator";
     	column[2] = "passwordAdministrator";
     	
     	value[0] = firstName;
     	value[1] = lastName;
     	value[2] = password;
     	
     	SQL.update(column, value, "Administrator", condition, "and");
	}
}
