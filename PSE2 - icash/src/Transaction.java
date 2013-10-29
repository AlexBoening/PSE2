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
    
    public Transaction(int amount, String description, Date date, boolean shownIn, 
    		           boolean shownOut, Account incomingAccount, Account outgoingAccount) {
        // ID generieren
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.shownIn = shownIn;
        this.shownOut = shownOut;
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
