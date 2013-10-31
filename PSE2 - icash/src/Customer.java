import java.util.ArrayList;

public class Customer extends Person {

	public Customer(String firstName, String secondName, String passwort) {
	    super(firstName, secondName, passwort, false);
	}
	
	public Customer(int id) {
		super(id, false);
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		String[] condition = {"idCustomer = " + id};
		this.firstName = firstName;
		SQL.update("firstNameCustomer", firstName, "Customer", condition, "and");
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		String[] condition = {"idCustomer = " + id};
		this.secondName = secondName;
		SQL.update("secondNameCustomer", secondName, "Customer", condition, "and");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		String[] condition = {"idCustomer = " + id};
		this.password = password;
		SQL.update("passwordCustomer", password, "Customer", condition, "and");
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
	
	public ArrayList<Account> getAccounts() {
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
}
