package classes;
import java.sql.SQLException;
import java.util.ArrayList;

public class Administrator extends Person {

	public Administrator(String firstName, String secondName, String password) throws SQLException {
	    super(firstName, secondName, password, true); 
	}
	
	public Administrator(int id) throws SQLException {
		super(id, true);
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void deactivateAccount(Account account, boolean active) throws SQLException {
		if (account.getAdministrator().getId() == id)
	        account.setFlagActive(active);	
		else {
			// Error: you cannot deactivate Accounts you do not maintain!
		}
	}
	
	public void createCustomer(String firstName, String secondName, String password) throws SQLException {
		Customer c = new Customer(firstName, secondName, password);
	}
	
	public void createAccount(boolean flagActive, Customer customer, Bank bank, AccountType accountType) throws SQLException {
		Account a = new Account (flagActive, customer, this, bank, accountType);
	}
	
	public void setFirstName(String firstName) throws SQLException {
		this.firstName = firstName;
		String[] condition = {"idAdministrator = " + id};
		SQL.update("firstNameAdministrator", firstName, "Administrator", condition, "and");
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String secondName) throws SQLException {
		String[] condition = {"idAdministrator = " + id};
		this.secondName = secondName;
		SQL.update("secondNameAdministrator", secondName, "administrator", condition, "and");
	}
	public String getPassword() {
		return password;
	}
	public void setPasswort(String password) throws SQLException {
		String[] condition = {"idAdministrator = " + id};
		this.password = password;
		SQL.update("passwordAdministrator", password, "administrator", condition, "and");
	}
	
	/*public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}*/
	public int getId() {
		return id;
	}
	/*public void setId(int id) {
		this.id = id;
	}*/
	
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
}
