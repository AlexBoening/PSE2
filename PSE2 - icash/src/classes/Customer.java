package classes;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * class for Customer objects
 * extends Person
 */
public class Customer extends Person {

	/**
	 * overloaded constructor for Customer
	 * @param firstName String
	 * @param lastName String
	 * @param password String
	 * @throws SQLException
	 */
	public Customer(String firstName, String lastName, String password) throws SQLException {
	    super(firstName, lastName, password, false);
	}
	
	/**
	 * overloaded constructor for Customer
	 * @param id int
	 * @throws SQLException
	 */
	public Customer(int id) throws SQLException {
		super(id, false);
	}
	
	/**
	 * constructor for Customer
	 */
	public Customer() {
		super();
	}
	
	/**
	 * deposit money on give account from this customer
	 * @param account Account
	 * @param amount int
	 * @param description String optional reason
	 * @throws SQLException
	 */
	public void depositMoney(Account account, int amount, String description) throws SQLException {
		Transaction t = new Transaction(amount, description, Convert.currentDate(), account, account.getBank().getBank_account());
	}
	
	public String getFirstName() {
		return firstName;
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
	 * 
	 * @return list of accounts associated with this customer
	 * @throws SQLException
	 */
	public ArrayList<Account> getAccounts() throws SQLException {
		if (accounts == null) {
			accounts = new ArrayList<Account>();
			String[] column = {"idAccount"};
			String[] condition = {"Customer_idCustomer = " + id};
			String[][] value = SQL.select(column, "Account", condition, "and");
			for (int i=0; i<value.length; i++)
			    accounts.add(new Account(Convert.toInt(value[i][0])));
		}
		return accounts;
	}
	
	/**
	 * logs the customer in
	 * @param password String
	 * @param a Account
	 * @throws SQLException
	 */
    public void login(String password, Account a) throws SQLException{
    	if (!a.isFlagActive()) {
    		setLoggedIn(false);
    		return;
    	}
    	String[] column = {"passwordCustomer"};
    	String table = "Customer";
    	String[] condition = {"idCustomer = " + id};
    	String[][] value = SQL.select(column, table, condition, "and");
    	if (value.length > 0)
    		if (value[0][0].equals(password))
    			setLoggedIn(true);
    		else
    			setLoggedIn(false);
    	else
    		setLoggedIn(false);
    }
    
	public void updateDB() throws SQLException {
		String[] condition = {"idCustomer = " + id};
		String[] column = new String[3];
		String[] value = new String[3];
		
		column[0] = "firstNameCustomer";
		column[1] = "lastNameCustomer";
     	column[2] = "passwordCustomer";
     	
     	value[0] = firstName;
     	value[1] = lastName;
     	value[2] = password;
     	
     	SQL.update(column, value, "Customer", condition, "and");
	}
}
