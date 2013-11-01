import java.util.ArrayList;

public class Customer extends Person {

	public Customer(String firstName, String secondName, String password) {
	    super(firstName, secondName, password, false);
	}
	
	public Customer(int id) {
		super(id, false);
	}
	
	
	public void withdrawMoney(Account account, int amount, String description){
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
	
	public void depositMoney(Account account, int amount, String description) {
		Transaction t = new Transaction(amount, description, Convert.currentDate(), account, account.getBank().getBank_account());
	}
	
	public void performTransaction(Account incomingAccount, Account outgoingAccount, int amount, String description) {
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
	
	public int getId() {
		return id;
	}
	
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
