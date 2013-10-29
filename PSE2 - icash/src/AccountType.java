
public class AccountType {

	private int id;
	private String description;
	private double interestRate;
	
	public AccountType(int id, String description, double interestRate) {
	    // ID generieren
	    this.description = description;
	    this.interestRate = interestRate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}
