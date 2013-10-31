
public class AccountType {

	private int id;
	private String description;
	private double interestRate;
	
	public AccountType(String description, double interestRate) {
	    this.id = SQL.getID("idAccountTyp", "AccountTyp");
	    this.description = description;
	    this.interestRate = interestRate;
	    
	    String[] value = new String[3];
	    value[0] = "" + id;
	    value[1] = description;
	    value[2] = "" + interestRate;
	    SQL.insert(value, "AccountTyp");
	}
	
	public AccountType(int id) {
		String[] column = {"idAccountTyp", "descriptionAccountTyp", "interestRateAccountTyp"};
        String[] condition = {"idAccountTyp = " + id};
        String[][] value = SQL.select(column, "AccountTyp", condition, "and");
        
        this.id = id;
        this.description = value[0][1];
        this.interestRate = Convert.toDouble(value[0][2]);
	}
	
	public int getId() {
		return id;
	}
	/*public void setId(int id) {
		this.id = id;
	}*/
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		String[] condition = {"idAccountTyp = " + id};
		SQL.update("descriptionAccountTyp", description, "AccountTyp", condition, "and");
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
		String[] condition = {"idAccountTyp = " + id};
		SQL.update("interestRateAccountTyp", "" + interestRate, "AccountTyp", condition, "and");
	}
}
