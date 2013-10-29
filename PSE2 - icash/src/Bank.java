import java.util.ArrayList;

public class Bank {
    private int id;
    private int blz;
    private String description;
    private ArrayList<Account> accounts;
    
    public Bank(int blz, String description) {
    	// ID generieren
    	this.blz = blz;
    	this.description = description;
    }
    
    public void add(Account a) {
        accounts.add(a);	
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBlz() {
		return blz;
	}
	public void setBlz(int blz) {
		this.blz = blz;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
}
