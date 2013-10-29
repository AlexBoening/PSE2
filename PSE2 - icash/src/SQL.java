import java.sql.*;

public class SQL {

	public static void getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
			Statement stmt = conn.createStatement();
		}
		catch (ClassNotFoundException err) {
			System.out.println("DB-Driver not found!");
			System.out.println(err);
		}
		catch (SQLException err) {
			System.out.println("Connect not possible");
			System.out.println(err);
		}
	}
}
