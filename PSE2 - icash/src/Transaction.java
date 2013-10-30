import java.sql.Date;

public class Transaction {
    private int id;
    private int amount;
    private String description;
    private Date date;
    private boolean shownIn;
    private boolean shownOut;
    private Account incomingAccount;
    private Account outgoingAccount;
    
    public Transaction(int amount, String description, Date date, Account incomingAccount, Account outgoingAccount) {
        this.id = SQL.getID("idTransaction", "Transaction");
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.shownIn = false;
        this.shownOut = false;
        this.incomingAccount = incomingAccount;
        this.outgoingAccount = outgoingAccount;
        
        String[] value = new String[8];
        value[0] = "" + id;
        value[1] = "" + amount;
        value[2] = description;
        value[3] = date.toString();
        value[4] = " ";
        value[5] = " ";
        value[6] = "" + incomingAccount.getId();
        value[7] = "" + outgoingAccount.getId();
        SQL.insert(value, "Transaction");
    }
    
    public Transaction(int id, Account incomingAccount, Account outgoingAccount) {
    	
    	String[] column = {"idTransaction", "amountTransaction", "descriptionTransaction", "dateTransaction",
    			           "shownInTransaction", "shownOutTransaction", "incomingAccountTransaction", "outgoingAccountTransaction"};
        String[] condition = {"idTransaction = " + id};
        String[][] value = SQL.select(column, "Transaction", condition, "and");

        this.id = id;
        this.amount = Convert.toInt(value[0][1]);
        this.description = value[0][2];
        this.date = Date.valueOf(value[0][3]);
        this.shownIn = value[0][4] == "X";
        this.shownOut = value[0][5] == "X";
        this.incomingAccount = incomingAccount;
        this.outgoingAccount = outgoingAccount;
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isShownIn() {
		return shownIn;
	}
	public void setShownIn(boolean shownIn) {
		this.shownIn = shownIn;
	}
	public boolean isShownOut() {
		return shownOut;
	}
	public void setShownOut(boolean shownOut) {
		this.shownOut = shownOut;
	}
	public Account getIncomingAccount() {
		return incomingAccount;
	}
	public void setIncomingAccount(Account incomingAccount) {
		this.incomingAccount = incomingAccount;
	}
	public Account getOutgoingAccount() {
		return outgoingAccount;
	}
	public void setOutgoingAccount(Account outgoingAccount) {
		this.outgoingAccount = outgoingAccount;
	}
}
