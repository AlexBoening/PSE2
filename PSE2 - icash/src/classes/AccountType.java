package classes;

import java.sql.SQLException;

/**
 * class AccountType
 *
 */
public class AccountType {

	private int id;
	private String description;
	private double interestRate;
	
	/**
	 * overloaded constructor for AccountType
	 * @param description String name of AccountType
	 * @param interestRate double 
	 * @throws SQLException
	 */
	public AccountType(String description, double interestRate) throws SQLException {
	    this.id = SQL.getID("idAccountTyp", "AccountTyp", "");
	    this.description = description;
	    this.interestRate = interestRate;
	    
	    String[] value = new String[3];
	    value[0] = "" + id;
	    value[1] = description;
	    value[2] = "" + interestRate;
	    SQL.insert(value, "AccountTyp");
	}
	
	/**
	 * constructor for AccountType
	 * @param id AccountType ID
	 * @throws SQLException
	 */
	public AccountType(int id) throws SQLException {
		String[] column = {"idAccountTyp", "descriptionAccountTyp", "interestRateAccountTyp"};
        String[] condition = {"idAccountTyp = " + id};
        String[][] value = SQL.select(column, "AccountTyp", condition, "and");
        
        if (value.length == 0)
        	return;
        this.id = id;
        this.description = value[0][1];
        this.interestRate = Convert.toDouble(value[0][2]);
	}
	
	public AccountType() {
		
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
	
	/**
	 * updates database
	 * @throws SQLException
	 */
	public void updateDB() throws SQLException {
		String[] column = new String[2];
		String[] value = new String[2];
		String[] condition = {"idAccountTyp = " + id};
		
		column[0] = "descriptionAccountTyp";
		column[1] = "interestRateAccountTyp";
		
		value[0] = description;
		value[1] = "" + interestRate;
		
		SQL.update(column, value, "AccountTyp", condition, "and");
	}
}
