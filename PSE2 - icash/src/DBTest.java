import java.sql.*;
import classes.*;

public class DBTest {
	
	public static void main(String[] args) throws SQLException{
		SQL.getConnection();
		/*String[] values = {"2", "Festgeld-Konto", "5.70"};
		
		SQL.insert(values, "accounttyp");*/
		/*String[] columns = {"descriptionAccountTyp", "interestRateAccountTyp"};
		String[] conditions = {"idAccountTyp = 1", "idAccountTyp <> 2"};
		String[][] result = SQL.select(columns, "accounttyp", conditions);
		for (int i=0; i<result.length; i++)
			for (int j=0; j<result[i].length; j++)
				System.out.print(result[i][j] + " ");
		SQL.update("descriptionAccountTyp", "iCash-Risiko", "accountTyp", conditions);
		//int i = Convert.toInt(result[0][0]);
		double d = Convert.toDouble(result[0][1]);*/
		
		/*AccountType giro = new AccountType("Giro-Konto", 0.5);
		Bank spasskasse = new Bank(33335, "Sparkasse Friedrichsdorf");
		Customer nils = new Customer("Nils", "Weidmann", "geheim");
		Administrator admin = new Administrator("Der", "Aufseher", "Big Brother");*/
		/*Account konto = new Account(1);
		System.out.println(konto.getAccountType().getDescription());
		System.out.println(konto.getAdministrator().getFirstName() + " " + konto.getAdministrator().getSecondName());
		System.out.println(konto.getBank().getDescription());
		System.out.println(konto.getCustomer().getFirstName() + " " + konto.getCustomer().getSecondName());
		Account festgeldKonto = new Account(2);
		Transaction ueberweisung = new Transaction(100000, "Anlage", new Date(113,10,31), konto, festgeldKonto);*/
		//Bank b = new Bank(1);
		//b.payInterests();
		Account a = new Account(1);
		a.getCustomer().depositMoney(a, 1000, "Einzahlung");
		a.getCustomer().withdrawMoney(a, 500, "Auszahlung");
		a.getCustomer().performTransaction(new Account(2), a, 500, "Überweisung");
		//a.printTransactions();
		//a.showTransactions();
	}
}
