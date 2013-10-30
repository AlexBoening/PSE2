import java.util.ArrayList;

public class Bank {
    private int id;
    private int blz;
    private String description;
    private ArrayList<Account> accounts;
    
    public Bank(int blz, String description) {
    	this.id = SQL.getID("idBank", "Bank");
    	this.blz = blz;
    	this.description = description;
    	this.accounts = new ArrayList<Account>();
    	
    	String[] value = new String[3];
    	value[0] = "" + id;
    	value[1] = "" + blz;
    	value[2] = description;
    	SQL.insert(value, "Bank");
    }
    
    public Bank(int id) {
    	
    	String[] column = {"idBank", "blzBank", "descriptionBank" };
        String[] condition = {"idBank = " + id};
        String[][] value = SQL.select(column, "Bank", condition, "and");

    	this.id = id;
    	this.blz = Convert.toInt(value[0][1]);
    	this.description = value[0][2];
    	this.accounts = new ArrayList<Account>();
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
