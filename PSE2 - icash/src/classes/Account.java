package classes;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Account class 
 *
 */
public class Account {
    private int id;
    private boolean flagActive;
    private Customer customer;
    private Administrator administrator;
    private Bank bank;
    private AccountType accountType;
    private ArrayList<Transaction> transactions;


/**
 * overloaded constructor for Account
 * @param flagActive boolean 
 * @param customer Customer
 * @param administrator Administrator
 * @param bank Bank
 * @param accountType AccountType
 * @throws SQLException
 */
public Account(boolean flagActive, Customer customer, Administrator administrator, 
		       Bank bank, AccountType accountType) throws SQLException {
    this.id = SQL.getID("idAccount", "Account", "bank_idBank = " + bank.getId());
    if (id == 1)
    	id += bank.getId() * 100000;
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

/**
 * constructor for Account class
 * @param id int ID of new Account
 * @throws SQLException
 */
public Account(int id) throws SQLException {
	String[] column = {"flagActive"};
	String[] condition = {"idAccount = " + id};
	String[][] value = SQL.select(column, "Account", condition, "and");
	if (value.length == 0)
		return;
	this.id = id;
	this.flagActive = value[0][0].equals("X");
}

/**
 * Empty Constructor for Clients without Database Connection
 */
public Account() {
}

/**
 * adds transaction to the account
 * @param t Transaction
 */
public void add(Transaction t) {
	if (transactions == null)
		transactions = new ArrayList<Transaction>();
    transactions.add(t);	
}

/**
 * gets current balance
 * @return int Balance in cents
 * @throws SQLException
 */
public int getBalance() throws SQLException {
    int balance = 0;
    Iterator<Transaction> it = getTransactions().iterator();
    Transaction t;
    while (it.hasNext()) {
    	t = it.next();
    	if (t.getIncomingAccount().getId() == id)
    		balance += t.getAmount();
    	if (t.getOutgoingAccount().getId() == id)
    		balance -= t.getAmount();
    }
    return balance;
}

/**
 * shows transactions
 * @throws SQLException
 */
public void showTransactions() throws SQLException {
	Iterator<Transaction> it = getTransactions().iterator();
    Transaction t;
    while (it.hasNext()) {
    	t = it.next();
    	// actual display
    }
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public boolean isFlagActive() {
	return flagActive;
}

public void setFlagActive(boolean flagActive) {
	this.flagActive = flagActive;
	
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


public void setCustomer(Customer customer) {
	this.customer = customer;
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


public void setAdministrator(Administrator administrator) {
	this.administrator = administrator;
	
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

public void setBank(Bank bank) {
	this.bank = bank;
}

public AccountType getAccountType() throws SQLException {
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
}


public ArrayList<Transaction> getTransactions() throws SQLException {
	if (transactions == null) {
		transactions = new ArrayList<Transaction>();
		String[] column = {"idTransaction", "amountTransaction", "descriptionTransaction", "dateTransaction", "sentIncomingTransaction", "sentOutgoingTransaction"};
		String[] condition = {"incomingAccount_idAccount = " + id, "outgoingAccount_idAccount = " + id};
		String[][] value = SQL.select(column, "Transaction", condition, "or");
		for (int i=0; i<value.length; i++) {
			Transaction t = new Transaction();
			t.setId(Convert.toInt(value[i][0]));
			t.setAmount(Convert.toInt(value[i][1]));
			t.setDescription(value[i][2]);
			String s = value[i][3].substring(0,10);
			t.setDate(Date.valueOf(value[i][3].substring(0, 10)));
			t.setSentIncoming(value[i][4].equals("X"));
			t.setSentOutgoing( value[i][5].equals("X"));
			transactions.add(t);
		}
		    
	}
	return transactions;
}

public void setTransactions(ArrayList<Transaction> transactions) {
	this.transactions = transactions;
}

/**
 * updates database
 * @throws SQLException
 */
public void updateDB() throws SQLException{
	String[] column = new String[5];
	String[] value = new String[5];
	String[] condition = {"idAccount = " + id};

	column[0] = "flagActive";
	column[1] = "Customer_idCustomer";
	column[2] = "Administrator_idAdministrator";
	column[3] = "Bank_idBank";
	column[4] = "AccountTyp_idAccountTyp";
	
	if (flagActive)
		value[0] = "X";
	else
		value[0] = " ";
	value[1] = "" + getCustomer().getId();
	value[2] = "" + getAdministrator().getId();
	value[3] = "" + getBank().getId();
	value[4] = "" + getAccountType().getId();
	
    SQL.update(column, value, "Account", condition, "and");
    }
}