package classes;
import java.sql.*;
import java.util.ArrayList;

public class SQL {
	
	public static Statement getConnection() throws SQLException, ClassNotFoundException {
		
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
			Statement stmt = conn.createStatement();

		return stmt;
	}
	
	public static void closeConnection(Statement stmt) throws SQLException{
		Connection conn = stmt.getConnection();
		conn.close();
	}
	
	public static String[][] select(String[] column, String table, String[] condition, String connector) 
			                 throws SQLException {
		
		ResultSet rs;
		Statement stmt;
		try {
			stmt = getConnection();
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[][] result;
		String[] line = new String[column.length];
		
		String sql = "select ";
		for (int i=0; i<column.length; i++) {
			if (i>0)
				sql += ", ";
			sql += column[i];
		}
		sql += " from " + table + " ";
		for (int i=0; i<condition.length; i++) 
			if (i==0)
				sql += "where " + condition[i] + " ";
			else
				sql += connector + " " + condition[i] + " ";
		    rs = stmt.executeQuery(sql);
		    rs.beforeFirst();
		    int j=0;
		    while (rs.next()) {
		    	j++;
		    	line = new String[column.length];
		    	for (int i=0; i<column.length; i++)
		    		line[i] = rs.getString(i+1);
		    	list.add(line);
		    }
		    closeConnection(stmt);
		    result = new String[j][column.length];
		    result = list.toArray(result);
		return result;
	}
	
	public static void insert(String[] value, String table) throws SQLException {
		
		ResultSet rs;
		Statement stmt;
		try {
			stmt = getConnection();
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		
		String sql = "insert into " + table + " values ('";
		for (int i=0; i<value.length; i++) {
			if (i>0)
				sql += "', '";
			sql += value[i];
		}
		sql += "')";
		    int lines = stmt.executeUpdate(sql);
		    closeConnection(stmt);
		    if (lines < 1)
		    	throw new SQLException();
	}
	
	public static void update(String[] column, String[] value, String table, String[] condition, String connector)
	                   throws SQLException {
		
		ResultSet rs;
		Statement stmt;
		try {
			stmt = getConnection();
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		
		String sql = "update " + table + " set " + column[0] + " = '" + value[0] + "' ";
		for (int i=1; i<value.length; i++)
			sql += ", " + column[i] + " = '" + value[i] + "' ";
		for (int i=0; i<condition.length; i++)
		    if (i==0)
		    	sql+= "where " + condition[i] + " ";
		    else
		    	sql += connector + " " + condition[i] + " ";
		int lines = stmt.executeUpdate(sql);
		closeConnection(stmt);
		if (lines < 1)
	    	throw new SQLException();
	}
	
	public static int getID(String column, String table) throws SQLException {
		
		ResultSet rs;
		Statement stmt;
		try {
			stmt = getConnection();
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		
		String sql = "select max( " + column + " ) from " + table;
		int id;
		
		    rs = stmt.executeQuery(sql);
		    rs.beforeFirst();
		    if (rs.next()) 
		    	id = rs.getInt(1) + 1;
		    else
		    	id = 1;
		    closeConnection(stmt);
		    return id;
	}
}
