package bank;

import java.sql.*;

public class TestDatabase {

	public static void main(String[] args) {
		
		String driver = "com.mysql.jdbc.Driver";
		String dbHost = "localhost";
		String dbName = "jmtbank";
		String dbUrl = "jdbc:mysql://"+ dbHost + ":3306/" + dbName;
		String dbUser = "testuser";
		String dbPass = "testpw";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
		} catch(ClassNotFoundException cnfe) {
			//TODO
		} catch(SQLException sqle) {
			//TODO
		}
		
		try { 
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM accounts WHERE account = '0010000001'";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.first()) {
				float balance = resultSet.getFloat("balance");
				String username = resultSet.getString("username");
				System.out.println("username = "+username+"\nold balance = "+balance);
				float newbalance = balance - 500;
				Statement update = conn.createStatement();
				String updatequery = "UPDATE accounts SET balance = " + newbalance + " WHERE account = '0010000001'";
				System.out.println("executing query");
				update.executeUpdate(updatequery);
				System.out.println("update complete");
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(query);
				if(rs.first()) {
					balance = rs.getFloat("balance");
					username = resultSet.getString("username");
					System.out.println("username = "+username+"\nnew balance = "+balance);
				}
				else { System.out.println("updated query has no results"); }
			}
			else { System.out.println("Cant find account 0010000001"); }
			conn.close();
		} catch(SQLException sqle) { System.out.println("something went wrong"); }
	}
}