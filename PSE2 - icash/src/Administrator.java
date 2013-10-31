import java.util.ArrayList;

public class Administrator extends Person {

	public Administrator(String firstName, String secondName, String passwort) {
	    super(firstName, secondName, passwort, true); 
	}
	
	public Administrator(int id) {
		super(id, true);
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		String[] condition = {"idAdministrator = " + id};
		SQL.update("firstNameAdministrator", firstName, "Administrator", condition, "and");
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String secondName) {
		String[] condition = {"idAdministrator = " + id};
		this.secondName = secondName;
		SQL.update("secondNameAdministrator", secondName, "administrator", condition, "and");
	}
	public String getPassword() {
		return password;
	}
	public void setPasswort(String password) {
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
	
	public ArrayList<Account> getAccounts() {
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
