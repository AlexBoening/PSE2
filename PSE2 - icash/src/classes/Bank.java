package classes;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Bank {
    private int id;
    private int blz;
    private String description;
    private ArrayList<Account> accounts;
    private Account bank_account;
    
    public Bank(int blz, String description) throws SQLException {
    	this.id = SQL.getID("idBank", "Bank");
    	this.blz = blz;
    	this.description = description;
    	this.accounts = new ArrayList<Account>();
    	//this.setBank_account(new Account(true, new Customer(1), new Administrator(1), this, new AccountType(1)));
    	
    	String[] value = new String[3];
    	value[0] = "" + id;
    	value[1] = "" + blz;
    	value[2] = description;
    	SQL.insert(value, "Bank");
    }
    
    public Bank(int id) throws SQLException {
    	
    	String[] column = {"idBank", "blzBank", "descriptionBank", "Account_idAccount" };
        String[] condition = {"idBank = " + id};
        String[][] value = SQL.select(column, "Bank", condition, "and");
        
        if (value.length == 0)
        	return;
    	this.id = id;
    	this.blz = Convert.toInt(value[0][1]);
    	this.description = value[0][2];
    	this.bank_account = new Account(Convert.toInt(value[0][3]));
    }
    
    public Bank() {
    	
    }
    
    public void add(Account a) {
    	if (accounts == null)
    		accounts = new ArrayList<Account>();
        accounts.add(a);	
    }
    
    public void payInterests() throws SQLException {
    	int balance = 0;
    	int interests = 0;
        Iterator<Account> it = getAccounts().iterator();
        Account a;
        Transaction t;
        while (it.hasNext()) {
        	a = it.next();
        	balance = a.getBalance();
        	interests = (int)(a.getAccountType().getInterestRate() * balance / 100);
        	if (interests != 0)
        	    t = new Transaction(interests, "Zinsen", Convert.currentDate(), a, bank_account);
        }
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
	
	public ArrayList<Account> getAccounts() throws SQLException {
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

	public Account getBank_account() {
		return bank_account;
	}

	public void setBank_account(Account bank_account) {
		this.bank_account = bank_account;
	}
	
	public void updateDB() throws SQLException {
		String[] condition = {"idBank = " + id};
		String[] column = new String[2];
		String[] value = new String[2];
		
		column[0] = "blzBank";
		column[1] = "descriptionBank";
		column[2] = "Account_idAccount";
		
		value[0] = "" + blz;
		value[1] = description;
		value[2] = "" + bank_account.getId();
		
		SQL.update(column, value, "Bank", condition, "and");
	}
}
