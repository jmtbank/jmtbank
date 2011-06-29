package bank;

import java.sql.*;

/** Create a simple table named "music" in the
 *  database specified on the command line. Uses the MySql
 *  Connector/J driver.
 *  <P>
 *  Adapted from Core Servlets and JavaServer Pages 2nd Edition
 */

public class CreateDatabase {
  public static void main(String[] args) {
    
    Connection connection = null;
    String driver = "com.mysql.jdbc.Driver";
    String host = "localhost";
    String dbName = "jmtbank";
    String url = "jdbc:mysql://"+ host + ":3306/" + dbName;
 
    String username = "testuser";
    String password = "testpw";
    String accountformat = "(account VARCHAR(10), username VARCHAR(16), balance FLOAT)";
	String clientsformat = "(username VARCHAR(16), password VARCHAR(16), accountid VARCHAR(10), firstname VARCHAR(20), lastname VARCHAR(30))";
	String cardsformat = "(cardid VARCHAR(10), accountid VARCHAR(10), pincode VARCHAR(5))";
    String[] accrows = new String[5];
	String[] clirows = new String[5];
	String[] carrows = new String[5];
	for(int i = 0; i<accrows.length; i++) {
		int j = i+1;
		accrows[i] = "('001000000"+j+"', 'user"+j+"', "+j*1000+")";
		clirows[i] = "('user"+j+"', 'password"+j+"', '001000000"+j+"', 'firstname"+j+"', 'lastname"+j+"')";
		carrows[i] = "('001000000"+j+"', '001000000"+j+"', '0000"+j+"')";
	}
    try {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username,
                                      password);
      } catch(ClassNotFoundException cnfe) {
        System.err.println("Error loading driver: " + cnfe);
      } catch(SQLException sqle) {
        System.err.println("Error connecting: " + sqle);
      }
    createTable(connection, "accounts", accountformat, accrows);
	createTable(connection, "clients", clientsformat, clirows);
	createTable(connection, "cards", cardsformat, carrows);
	
	/*try {
	Statement getaccountstate = connection.createStatement();
		String query = "SELECT * FROM accounts WHERE account = '0010000001'";
		ResultSet resultSet = getaccountstate.executeQuery(query);
		if(resultSet.first()) {
		String accuser = resultSet.getString("username");
		float accbalance = resultSet.getFloat("balance");
		System.out.println("accuser = "+accuser+"\naccbalance = "+accbalance); }
	} catch(SQLException sqle) { System.out.println("SQLE in getaccountstate");}
    try {
      connection.close();
    } catch(SQLException sqle2) {
      System.err.println("Problem closing connection: " + sqle2);
    } */
  }

  /** Build a table with the specified format and rows. */

  private static void createTable(Connection connection,
                                  String tableName,
                                  String tableFormat,
                                  String[] tableRows) {
    try {
      Statement statement = connection.createStatement();
      // Drop previous table if it exists, but don't get
      // error if not. Thus, the separate try/catch here.
      try {
        statement.execute("DROP TABLE " + tableName);
      } catch(SQLException sqle) {}
      String createCommand =
        "CREATE TABLE " + tableName + " " + tableFormat + " ENGINE = InnoDB";
      statement.execute(createCommand);
      String insertPrefix =
        "INSERT INTO " + tableName + " VALUES ";
      for(int i=0; i<tableRows.length; i++) {
        statement.execute(insertPrefix + tableRows[i]);
      }
    } catch(SQLException sqle) {
      System.err.println("Error creating table: " + sqle);
    }
  } 
}  