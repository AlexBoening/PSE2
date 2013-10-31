import java.sql.Date;

public class Transaction {
    private int id;
    private int amount;
    private String description;
    private Date date;
    private Account incomingAccount;
    private Account outgoingAccount;    
    private boolean shownIn;
    private boolean shownOut;
    
    public Transaction(int amount, String description, Date date, Account incomingAccount, Account outgoingAccount) {
        this.id = SQL.getID("idTransaction", "Transaction");
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.shownIn = false;
        this.shownOut = false;
        this.incomingAccount = incomingAccount;
        incomingAccount.add(this);
        this.outgoingAccount = outgoingAccount;
        outgoingAccount.add(this);
        
        String[] value = new String[8];
        value[0] = "" + id;
        value[1] = "" + amount;
        value[2] = description;
        value[3] = date.toString();
        value[4] = "" + incomingAccount.getId();
        value[5] = "" + outgoingAccount.getId();
        value[6] = " ";
        value[7] = " ";
        SQL.insert(value, "Transaction");
    }
    
    public Transaction(int id) {
    	
    	String[] column = {"amountTransaction", "descriptionTransaction", "dateTransaction", "shownInTransaction", "shownOutTransaction"};
        String[] condition = {"idTransaction = " + id};
        String[][] value = SQL.select(column, "Transaction", condition, "and");

        this.id = id;
        this.amount = Convert.toInt(value[0][0]);
        this.description = value[0][1];
        this.date = Date.valueOf(value[0][2]);
        this.shownIn = value[0][3] == "X";
        this.shownOut = value[0][4] == "X";
    }
    
	public int getId() {
		return id;
	}
	
	/*public void setId(int id) {
		this.id = id;
	}*/
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		String[] condition = {"idTransaction = " + id};
		this.amount = amount;
		SQL.update("amountTransaction", "" + amount, "Transaction", condition, "and");
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		String[] condition = {"idTransaction = " + id};
		this.description = description;
		SQL.update("descriptionTransaction", description, "Transaction", condition, "and");
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		String[] condition = {"idTransaction = " + id};
		this.date = date;
		SQL.update("dateTransaction", date.toString(), "Transaction", condition, "and");
	}
	
	public boolean isShownIn() {
		return shownIn;
	}
	
	public void setShownIn(boolean shownIn) {
		String[] condition = {"idTransaction = " + id};
		this.shownIn = shownIn;
		if (shownIn)
			SQL.update("shownInTransaction", "X", "Transaction", condition, "and");
		else
			SQL.update("shownInTransaction", " ", "Transaction", condition, "and");
	}
	
	public boolean isShownOut() {
		return shownOut;
	}
	
	public void setShownOut(boolean shownOut) {
		String[] condition = {"idTransaction = " + id};
		this.shownOut = shownOut;
		if (shownOut) 
			SQL.update("shownOutTransaction", "X", "Transaction", condition, "and");
		else
			SQL.update("shownOutTransaction", " ", "Transaction", condition, "and");
	}
	
	public Account getIncomingAccount() {
		
		if (incomingAccount == null) {
			String[] column = {"incomingAccount_idAccount"};
			String[] condition = {"idTransaction = " + id};
			String[][] value = SQL.select(column, "Transaction", condition, "and");
			incomingAccount = new Account(Convert.toInt(value[0][0]));
		}
		return incomingAccount;
	}
	
	public void setIncomingAccount(Account incomingAccount) {
		String[] condition = {"idTransaction = " + id};
		this.incomingAccount = incomingAccount;
		SQL.update("incomingAccount_idAccount", "" + incomingAccount.getId(), "Transaction", condition, "and");
	}
	
	public Account getOutgoingAccount() {
		if (outgoingAccount == null) {
			String[] column = {"outgoingAccount_idAccount"};
			String[] condition = {"idTransaction = " + id};
			String[][] value = SQL.select(column, "Transaction", condition, "and");
			outgoingAccount = new Account(Convert.toInt(value[0][0]));
		}
		return outgoingAccount;
	}
	
	public void setOutgoingAccount(Account outgoingAccount) {
		String[] condition = {"idTransaction = " + id};
		this.outgoingAccount = outgoingAccount;
		SQL.update("outgoingAccount_idAccount", "" + outgoingAccount.getId(), "Transaction", condition, "and");
	}
}
