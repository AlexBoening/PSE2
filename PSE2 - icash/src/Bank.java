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
    }
    
    public void add(Account a) {
    	if (accounts == null)
    		accounts = new ArrayList<Account>();
        accounts.add(a);	
    }
    
	public int getId() {
		return id;
	}
	
	/*public void setId(int id) {
		this.id = id;
	}*/
	
	public int getBlz() {
		return blz;
	}
	
	public void setBlz(int blz) {
		this.blz = blz;
		String[] condition = {"idBank = " + id};
		SQL.update("blzBank", "" + blz, "Bank", condition, "and");
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
		String[] condition = {"idBank = " + id};
		SQL.update("descriptionBank", description, "Bank", condition, "and");
	}
	
	public ArrayList<Account> getAccounts() {
		if (accounts == null) {
			accounts = new ArrayList<Account>();
			String[] column = {"idAccount"};
			String[] condition = {"Bank_idBank = " + id};
			String[][] value = SQL.select(column, "Account", condition, "and");
			for (int i=0; i<value.length; i++)
			    accounts.add(new Account(Convert.toInt(value[i][0])));
		}
		return accounts;
	}
	
	/*public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}*/
}
