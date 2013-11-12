package classes;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer extends Person {

	public Customer(String firstName, String secondName, String password) throws SQLException {
	    super(firstName, secondName, password, false);
	}
	
	public Customer(int id) throws SQLException {
		super(id, false);
	}
	
	public Customer() {
		super();
	}
	
	public void withdrawMoney(Account account, int amount, String description) throws SQLException {
		if (account.getCustomer().getId() == id) {
			int balance = account.getBalance();
			if (balance >= amount) {
			    Transaction t = new Transaction(amount, description, Convert.currentDate(), account.getBank().getBank_account(), account);
			}
			else {
				// Error Message: Balance must not be negative
			}
		}
		else {
			// Error Message: You cannot withdraw Money from an account you do not own!
		}
	}
	
	public void depositMoney(Account account, int amount, String description) throws SQLException {
		Transaction t = new Transaction(amount, description, Convert.currentDate(), account, account.getBank().getBank_account());
	}
	
	public void performTransaction(Account incomingAccount, Account outgoingAccount, int amount, String description) throws SQLException {
		if (outgoingAccount.getCustomer().getId() == id) {
			int balance = outgoingAccount.getBalance();
			if (balance >= amount) {
			    Transaction t = new Transaction(amount, description, Convert.currentDate(), incomingAccount, outgoingAccount);
			}
			else {
				// Error Message: Balance must not be negative
			}
		}
		else {
			// Error Message: You cannot transfer Money from an account you do not own!
		}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	
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
	
    public void login(String password) throws SQLException{
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
		column[1] = "secondNameCustomer";
     	column[2] = "passwordCustomer";
     	
     	value[0] = firstName;
     	value[1] = secondName;
     	value[2] = password;
     	
     	SQL.update(column, value, "Customer", condition, "and");
	}
}
