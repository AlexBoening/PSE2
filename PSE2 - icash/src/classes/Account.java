package classes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Account {
    private int id;
    private boolean flagActive;
    private Customer customer;
    private Administrator administrator;
    private Bank bank;
    private AccountType accountType;
    private ArrayList<Transaction> transactions;


public Account(boolean flagActive, Customer customer, Administrator administrator, 
		       Bank bank, AccountType accountType) throws SQLException {
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

public Account(int id) throws SQLException {
	String[] column = {"flagActive"};
	String[] condition = {"idAccount = " + id};
	String[][] value = SQL.select(column, "Account", condition, "and");
	if (value.length == 0)
		return;
	this.id = id;
	this.flagActive = value[0][0].equals("X");
}

public void add(Transaction t) {
	if (transactions == null)
		transactions = new ArrayList<Transaction>();
    transactions.add(t);	
}

public int getBalance() throws SQLException {
    int balance = 0;
    Iterator<Transaction> it = getTransactions().iterator();
    Transaction t;
    while (it.hasNext()) {
    	t = it.next();
    	if (t.getIncomingAccount().getId() == id)
    		balance += t.getAmount();
    	else if (t.getOutgoingAccount().getId() == id)
    		balance -= t.getAmount();
    }
    return balance;
}

public void printTransactions() throws SQLException {
	
	Iterator<Transaction> it = getTransactions().iterator();
    Transaction t;
    while (it.hasNext()) {
    	t = it.next();
    	if (t.getIncomingAccount().getId() == id && !t.isShownIncoming()) {
    		// actual PrintOut
    		t.setShownIncoming(true);
    	}
    	else if (t.getOutgoingAccount().getId() == id && !t.isShownOutgoing()) {
    		// actual PrintOut;
    		t.setShownOutgoing(true);
    	}
    }
}

public void showTransactions() throws SQLException {
	Iterator<Transaction> it = getTransactions().iterator();
    Transaction t;
    while (it.hasNext()) {
    	t = it.next();
    	// actual display
    }
}

public boolean isFlagActive() {
	return flagActive;
}

public void setFlagActive(boolean flagActive) throws SQLException {
	this.flagActive = flagActive;
	String[] condition = {"idAccount = " + id};
	if (flagActive)
		SQL.update("flagActive", "X", "Account", condition, "and");
	else
		SQL.update("flagActive", " ", "Account", condition, "and");
}

public Customer getCustomer() throws SQLException {
	if (customer == null) {
		String[] column = {"Customer_idCustomer"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		customer = new Customer(Convert.toInt(value[0][0]));
	}
	return customer;
}


public void setCustomer(Customer customer) throws SQLException {
	this.customer = customer;
	String[] condition = {"idAccount = " + id};
	SQL.update("Customer_idCustomer", "" + customer.getId(), "Account", condition, "and");
}

public Administrator getAdministrator() throws SQLException {
	if (administrator == null) {
		String[] column = {"Administrator_idAdministrator"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		administrator = new Administrator(Convert.toInt(value[0][0]));
	}
	return administrator;
}


public void setAdministrator(Administrator administrator) throws SQLException {
	this.administrator = administrator;
	String[] condition = {"idAccount = " + id};
	SQL.update("Administrator_idAdministrator", "" + administrator.getId(), "Account", condition, "and");
}

public Bank getBank() throws SQLException {
	if (bank == null) {
		String[] column = {"Bank_idBank"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		bank = new Bank(Convert.toInt(value[0][0]));
	}
	return bank;
}

public void setBank(Bank bank) throws SQLException {
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

public AccountType getAccountType() throws SQLException {
	if (accountType == null) {
		String[] column = {"AccountTyp_idAccountTyp"};
		String[] condition = {"idAccount = " + id};
		String[][] value = SQL.select(column, "Account", condition, "and");
		accountType = new AccountType(Convert.toInt(value[0][0]));
	}
	return accountType;
}


public void setAccountType(AccountType accountType) throws SQLException {
	this.accountType = accountType;
	String[] condition = {"idAccount = " + id};
	SQL.update("AccountTyp_idAccountTyp", "" + accountType.getId(), "Account", condition, "and");
}


public ArrayList<Transaction> getTransactions() throws SQLException {
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