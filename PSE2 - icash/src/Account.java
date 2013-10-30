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
    this.setFlagActive(flagActive);
    this.setCustomer(customer);
    this.setAdministrator(administrator);
    this.setBank(bank);
    this.setAccountType(accountType);
    this.setTransactions(new ArrayList<Transaction>());
    
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

public Account(int id, Customer customer, Administrator administrator, Bank bank, AccountType accountType) {
	String[] column = {"idAccount", "flagActiveAccount", "Customer_idCustomer", "Administrator_idAdministrator",
			           "Bank_idBank", "AccountTyp_idAccount_Typ"};
	String[] condition = {"idAccount = " + id};
	String[][] value = SQL.select(column, "Account", condition, "and");
	
	this.id = id;
	this.flagActive = value[0][1] == "X";
	this.customer = customer;
	customer.add(this);
	this.administrator = administrator;
	administrator.add(this);
	this.bank = bank;
	bank.add(this);
	this.accountType = accountType;
	this.transactions = new ArrayList<Transaction>();
}

public void add(Transaction t) {
    transactions.add(t);
}

public boolean isFlagActive() {
	return flagActive;
}


public void setFlagActive(boolean flagActive) {
	this.flagActive = flagActive;
}


public Customer getCustomer() {
	return customer;
}


public void setCustomer(Customer customer) {
	this.customer = customer;
}


public Administrator getAdministrator() {
	return administrator;
}


public void setAdministrator(Administrator administrator) {
	this.administrator = administrator;
}


public Bank getBank() {
	return bank;
}


public void setBank(Bank bank) {
	this.bank = bank;
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public AccountType getAccountType() {
	return accountType;
}


public void setAccountType(AccountType accountType) {
	this.accountType = accountType;
}


public ArrayList<Transaction> getTransactions() {
	return transactions;
}


public void setTransactions(ArrayList<Transaction> transactions) {
	this.transactions = transactions;
}


}