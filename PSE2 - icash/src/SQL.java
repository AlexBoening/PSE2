import java.sql.*;
import java.util.ArrayList;

public class SQL {

	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;
	
	public static void getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
			stmt = conn.createStatement();
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
	
	public static String[][] select(String[] column, String table, String[] condition) {
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[][] result;
		String[] line = new String[column.length];
		
		String sql = "select ";
		for (int i=0; i<column.length; i++) {
			if (i>0)
				sql += ", ";
			sql += column[i];
		}
		sql += "from " + table;
		for (int i=0; i<condition.length; i++) 
			if (i==0)
				sql += "where " + condition[i] + " ";
			else
				sql += "and " + condition[i] + " ";
		try {
		    rs = stmt.executeQuery(sql);
		    rs.beforeFirst();
		    int j=0;
		    while (rs.next()) {
		    	j++;
		    	for (int i=0; i<column.length; i++)
		    		line[i] = rs.getString(i+1);
		    	list.add(line);
		    }
		    result = new String[j][column.length];
		    result = list.toArray(result);
		}
		catch (SQLException err) {
			System.out.println("Connection not possible");
			result = new String[0][0];
		}
		return result;
	}
	
	public static void insert(String[] value, String table) {
		
		String sql = "insert into " + table + " values ('";
		for (int i=0; i<value.length; i++) {
			if (i>0)
				sql += "', '";
			sql += value[i];
		}
		sql += "')";
		try {
		    int lines = stmt.executeUpdate(sql);
		}
		catch (SQLException err) {
			System.out.println("Connection not possible");
		}
	}
	
	public static void update(String column, String value, String table, String[] condition) {
		
		String sql = "update " + table + " set " + column + " = " + value + " ";
		for (int i=0 ; i<condition.length; i++) {
			if (i==0) 
				sql += "where " + condition[i] + " ";
			else
				sql += "and " + condition[i] + " ";
		}
		try {
		    int lines = stmt.executeUpdate(sql);
		}
		catch (SQLException err) {
			System.out.println("Connection not possible");
		}
	}
}
