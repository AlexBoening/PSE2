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
    // ID generieren
    this.setFlagActive(flagActive);
    this.setCustomer(customer);
    this.setAdministrator(administrator);
    this.setBank(bank);
    this.setAccountType(accountType);
    this.setTransactions(new ArrayList<Transaction>());
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