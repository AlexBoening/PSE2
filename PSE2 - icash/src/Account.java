import java.util.ArrayList;

public class Account {
    private int id;
    private boolean flagActive;
    private Customer customer;
    private Administrator administrator;
    private Bank bank;
    private AccountType accountType;
    private ArrayList<Transaction> transactions;


public Account(boolean flagActive, Customer customer, Administrator administrator, 
		       Bank bank, AccountType accountType) {
    this.id = SQL.getID("idAccount", "Account");
    this.flagActive = flagActive;
    this.customer = customer;
    customer.add(this);
    this.administrator = administrator;
    administrator.add(this);
    this.bank = bank;
    bank.add(this);
    this.accountType = accountType;
    this.transactions = new ArrayList<Transaction>();
    
    String[] value = new String[6];
    value[0] = "" + this.id;
    if (flagActive)
        value[1] = "X";
    else
    	value[1] = " ";
    value[2] = "" + customer.getId();
    value[3] = "" + administrator.getId();
    value[4] = "" + bank.getId();
    value[5] = "" + accountType.getId();
    SQL.insert(value, "Account");
    }

public Account(int id) {
	String[] column = {"flagActive"};
	String[] condition = {"idAccount = " + id};
	String[][] value = SQL.select(column, "Account", condition, "and");
	
	this.id = id;
	this.flagActive = value[0][0] == "X";
}

public void add(Transaction t) {
	if (transactions == null)
		transactions = new ArrayList<Transaction>();
    transactions.add(t);	
}

public boolean isFlagActive() {
	return flagActive;
}

public void setFlagActive(boolean flagActive) {
	this.flagActive = flagActive;
	String[] condition = {"idAccount = " + id};
	if (flagActive)
		SQL.update("flagActive", "X", "Account", condition, "and");
	else
		SQL.update("flagActive", " ", "Account", condition, "and");
}

public Customer getCustomer() {
	if (customer == null) {
		String[] column = {"Customer_idCustomer"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		customer = new Customer(Convert.toInt(value[0][0]));
	}
	return customer;
}


public void setCustomer(Customer customer) {
	this.customer = customer;
	String[] condition = {"idAccount = " + id};
	SQL.update("Customer_idCustomer", "" + customer.getId(), "Account", condition, "and");
}

public Administrator getAdministrator() {
	if (administrator == null) {
		String[] column = {"Administrator_idAdministrator"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		administrator = new Administrator(Convert.toInt(value[0][0]));
	}
	return administrator;
}


public void setAdministrator(Administrator administrator) {
	this.administrator = administrator;
	String[] condition = {"idAccount = " + id};
	SQL.update("Administrator_idAdministrator", "" + administrator.getId(), "Account", condition, "and");
}

public Bank getBank() {
	if (bank == null) {
		String[] column = {"Bank_idBank"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		bank = new Bank(Convert.toInt(value[0][0]));
	}
	return bank;
}

public void setBank(Bank bank) {
	this.bank = bank;
	String[] condition = {"idAccount = " + id};
	SQL.update("Bank_idBank", "" + bank.getId(), "Account", condition, "and");
}


public int getId() {
	return id;
}

/*public void setId(int id) {
	this.id = id;
}*/

public AccountType getAccountType() {
	if (accountType == null) {
		String[] column = {"AccountTyp_idAccountTyp"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		accountType = new AccountType(Convert.toInt(value[0][0]));
	}
	return accountType;
}


public void setAccountType(AccountType accountType) {
	this.accountType = accountType;
	String[] condition = {"idAccount = " + id};
	SQL.update("AccountTyp_idAccountTyp", "" + accountType.getId(), "Account", condition, "and");
}


public ArrayList<Transaction> getTransactions() {
	if (transactions == null) {
		transactions = new ArrayList<Transaction>();
		String[] column = {"idTransaction"};
		String[] condition = {"incomingAccount_idAccount = " + id, "outgoingAccount_idAccount = " + id};
		String[][] value = SQL.select(column, "Transaction", condition, "or");
		for (int i=0; i<value.length; i++)
		    transactions.add(new Transaction(Convert.toInt(value[i][0])));
	}
	return transactions;
}

/*
public void setTransactions(ArrayList<Transaction> transactions) {
	this.transactions = transactions;
}
*/

}