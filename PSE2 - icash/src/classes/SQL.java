package classes;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * SQL class to perform SQL commands
 * 
 */
public class SQL {
	
	private static Connection conn;
	private static Statement stmt;
	private static Lock lock;
	
	/**
	 * initializes the SQL connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void init() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
		stmt = conn.createStatement();
		lock = new ReentrantLock(true);
	}
	
	/**
	 * executes select statement with given parameters
	 * @param column String column in table
	 * @param table String table to fetch data from
	 * @param condition String limiting results
	 * @param connector String
	 * @return results from select query
	 * @throws SQLException
	 */
	public static String[][] select(String[] column, String table, String[] condition, String connector) 
			                 throws SQLException {
		
		String[][] result = null;
		ResultSet rs = null;

		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] line = new String[column.length];
		int j=0;
		
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
		
			synchronized (lock) {
				rs = stmt.executeQuery(sql);
				rs.beforeFirst();
				
				while (rs.next()) {
					j++;
					line = new String[column.length];
					for (int i=0; i<column.length; i++)
						line[i] = rs.getString(i+1);
					list.add(line);
				}
			}
		    result = new String[j][column.length];
		    result = list.toArray(result);
		return result;
	}
	
	/**
	 * inserts new data in give table
	 * @param value String[] new values
	 * @param table String table to add data into
	 * @throws SQLException
	 */
	public static void insert(String[] value, String table) throws SQLException {
		
		ResultSet rs = null;
		int lines = 0;
		String sql = "insert into " + table + " values ('";
		for (int i=0; i<value.length; i++) {
			if (i>0)
				sql += "', '";
			sql += value[i];
		}
		sql += "')";
			synchronized (lock) {
				lines = stmt.executeUpdate(sql);
			}
		    if (lines < 1)
		    	throw new SQLException();
		}
	
	/**
	 * update data record in given table
	 * @param column String[] columns to add
	 * @param value String[] overwrite values with this new values
	 * @param table String table
	 * @param condition String limiting datarows to update
	 * @param connector String
	 * @throws SQLException
	 */
	public static void update(String[] column, String[] value, String table, String[] condition, String connector)
	                   throws SQLException {
		
		ResultSet rs = null;
		int lines = 0;
		String sql = "update " + table + " set " + column[0] + " = '" + value[0] + "' ";
		for (int i=1; i<value.length; i++)
			sql += ", " + column[i] + " = '" + value[i] + "' ";
		for (int i=0; i<condition.length; i++)
		    if (i==0)
		    	sql+= "where " + condition[i] + " ";
		    else
		    	sql += connector + " " + condition[i] + " ";
		synchronized (lock) {
			lines = stmt.executeUpdate(sql);
		}
		if (lines < 1)
	    	throw new SQLException();
	}
	
	/**
	 * gets last entry in table matching given condition
	 * @param column String 
	 * @param table String
	 * @param condition String
	 * @return last entry
	 * @throws SQLException
	 */
	public static int getID(String column, String table, String condition) throws SQLException {
		
		int id = 0;
		ResultSet rs = null;
			String sql = "select max( " + column + " ) from " + table;
			if (condition != null && !condition.equals(""))
				sql += " where " + condition;
			synchronized (lock) {
				rs = stmt.executeQuery(sql);
				rs.beforeFirst();
				if (rs.next()) 
					id = rs.getInt(1) + 1;
				else
					id = 1;
			}
		return id;
	}
}
