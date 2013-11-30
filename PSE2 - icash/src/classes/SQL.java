package classes;
import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class SQL {
	
	public static String[][] select(String[] column, String table, String[] condition, String connector) 
			                 throws SQLException {
		
		
		String[][] result = null;
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
			stmt = conn.createStatement();

		ArrayList<String[]> list = new ArrayList<String[]>();
		
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
		    
		    result = new String[j][column.length];
		    result = list.toArray(result);
		
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		finally {
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
					if (conn != null)
						conn.close();
				}
				catch (SQLException e) {
					//Logger log = Logger.getRootLogger();
					//log.error("Database connection could not be closed!");
				}
		}
		return result;
	}
	
	public static void insert(String[] value, String table) throws SQLException {
		
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
			stmt = conn.createStatement();
		String sql = "insert into " + table + " values ('";
		for (int i=0; i<value.length; i++) {
			if (i>0)
				sql += "', '";
			sql += value[i];
		}
		sql += "')";
		    int lines = stmt.executeUpdate(sql);
		    if (lines < 1)
		    	throw new SQLException();
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e) {
				//Logger log = Logger.getRootLogger();
				//log.error("Database connection could not be closed!");
			}
		}
	}
	
	public static void update(String[] column, String[] value, String table, String[] condition, String connector)
	                   throws SQLException {
		
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
			stmt = conn.createStatement();
		String sql = "update " + table + " set " + column[0] + " = '" + value[0] + "' ";
		for (int i=1; i<value.length; i++)
			sql += ", " + column[i] + " = '" + value[i] + "' ";
		for (int i=0; i<condition.length; i++)
		    if (i==0)
		    	sql+= "where " + condition[i] + " ";
		    else
		    	sql += connector + " " + condition[i] + " ";
		int lines = stmt.executeUpdate(sql);
		if (lines < 1)
	    	throw new SQLException();
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e) {
				//Logger log = Logger.getRootLogger();
				//log.error("Database connection could not be closed!");
			}
		}
	}
	
	public static int getID(String column, String table, String condition) throws SQLException {
		
		int id = 0;
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
			stmt = conn.createStatement();
			String sql = "select max( " + column + " ) from " + table;
			if (condition != null && !condition.equals(""))
				sql += " where " + condition;
		    rs = stmt.executeQuery(sql);
		    rs.beforeFirst();
		    if (rs.next()) 
		    	id = rs.getInt(1) + 1;
		    else
		    	id = 1;
		}
		catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e) {
				//Logger log = Logger.getRootLogger();
				//log.error("Database connection could not be closed!");
			}
		}
		return id;
	}
}
