package bank.access;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import bank.Account;
import bank.BankCard;
import bank.Client;

import java.sql.*;

public class DataAccessImpl implements DataAccess {
	private Map<String,Account> accounts;
	private Map<String,Client> clients;
	private Map<String,BankCard> cards;
	private Connection conn;
	private String driver;
	private String dbHost;
	private String dbUrl;
	private String dbName;
	private String dbUser;
	private String dbPass;

	public DataAccessImpl() {
//		final int n = 5; // niet meer dan 9 van maken
//		accounts = new HashMap<String,Account>();
//		clients = new HashMap<String,Client>();
//		cards = new HashMap<String,BankCard>();
//		for(int i = 1; i <= n; i++) {
//			accounts.put("001000000"+i, new Account("001000000"+i,"user"+i,i*1000));
//			clients.put("user"+i, new Client("user"+i,"password"+i,"001000000"+i, "firstname"+i, "lastname"+i));
//			cards.put("001000000"+i, new BankCard("001000000"+i,"001000000"+i,"0000"+i));
//		}
		conn = null;
		
		driver = "com.mysql.jdbc.Driver";
		dbHost = "localhost";
		dbName = "jmtbank";
		dbUrl = "jdbc:mysql://"+ dbHost + ":3306/" + dbName;
		dbUser = "testuser";
		dbPass = "testpw";
 	}

	/** Retrieves account information.
     * @param accountId account id
     * @return information about the account identified by 'accountId',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public Account getAccount(String accountId) throws RemoteException, DataAccessException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
		} catch(ClassNotFoundException cnfe) {
			//TODO
		} catch(SQLException sqle) {
			//TODO
		}
		Account account = null;
		try { 
			Statement getaccountstate = conn.createStatement();
			String query = "SELECT * FROM accounts WHERE account = '"+accountId+"'";
			ResultSet resultSet = getaccountstate.executeQuery(query);
			if(resultSet.first()) {
				String accuser = resultSet.getString("username");
				float accbalance = resultSet.getFloat("balance");
				account = new Account(accountId, accuser, accbalance);
			}
		} catch(SQLException sqle) { }
		try { conn.close(); }
		catch(SQLException sqle) {
			//TODO
		}
		return account;
	}

	/** Retrieves client information
     * @param username user name
     * @return information about the client identified by 'username',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public Client getClient(String username) throws RemoteException, DataAccessException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
		} catch(ClassNotFoundException cnfe) {
			//TODO
		} catch(SQLException sqle) {
			//TODO
		}
		
		Client client = null;
		try { 
			Statement getclientstate = conn.createStatement();
			String query = "SELECT * FROM clients WHERE username = '"+username+"'";
			ResultSet resultSet = getclientstate.executeQuery(query);
			if(resultSet.first()) {
				String password = resultSet.getString("password");
				String accountid = resultSet.getString("accountid");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				client = new Client(username, password, accountid, firstname, lastname);
			}
		} catch(SQLException sqle) { }
		
		try { conn.close(); }
		catch(SQLException sqle) {
			//TODO
		}
		return client;
	}


	/** Retrieves bank card information
     * @param cardId card id
     * @return information about the bank card identified by 'cardId',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public BankCard getBankCard(String cardId) throws RemoteException, DataAccessException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
		} catch(ClassNotFoundException cnfe) {
			//TODO
		} catch(SQLException sqle) {
			//TODO
		}
		
		BankCard bankcard = null;
		try { 
			Statement getcardstate = conn.createStatement();
			String query = "SELECT * FROM cards WHERE cardid = '"+cardId+"'";
			ResultSet resultSet = getcardstate.executeQuery(query);
			if(resultSet.first()) {
				String accountid = resultSet.getString("accountid");
				String pincode = resultSet.getString("pincode");
				bankcard = new BankCard(cardId, accountid, pincode);
			}
		} catch(SQLException sqle) { }
		
		try { conn.close(); }
		catch(SQLException sqle) {
			//TODO
		}
		return bankcard;
	}


	/** Subtracts amount 'amount' from the balance of the account identified
	 * by 'accountId'
     * @param accountId account id
     * @param amount amount to be subtracted
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void debitAccount(String accountId, float amount) throws RemoteException, DataAccessException {
		try { 
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM accounts WHERE account = '"+accountId+"'";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.first()) {
				float balance = resultSet.getFloat("balance");
				float newbalance = balance - amount;
				Statement update = conn.createStatement();
				String updatequery = "UPDATE accounts SET balance = " + newbalance + " WHERE account = '"+accountId+"'";
				update.executeUpdate(updatequery);

			}
			else { throw new DataAccessException("Unknown account: "+accountId); }
		} catch(SQLException sqle) { }
		
		
		/*Account acc = accounts.get(accountId);
		if(acc == null) {
			throw new DataAccessException("Unknown account: " + accountId);
		}
		acc.setBalance(acc.getBalance() - amount);*/
	}


	/** Adds amount 'amount' to the balance of the account identified
	 * by 'accountId'
     * @param accountId account id
     * @param amount amount to be added
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void creditAccount(String accountId, float amount) throws RemoteException, DataAccessException {
		try { 
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM accounts WHERE account = '"+accountId+"'";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.first()) {
				float balance = resultSet.getFloat("balance");
				float newbalance = balance + amount;
				Statement update = conn.createStatement();
				String updatequery = "UPDATE accounts SET balance = " + newbalance + " WHERE account = '"+accountId+"'";
				update.executeUpdate(updatequery);

			}
			else { throw new DataAccessException("Unknown account: "+accountId); }
		} catch(SQLException sqle) { }
		
		/*Account acc = accounts.get(accountId);
		if(acc == null) {
			throw new DataAccessException("Unknown account: " + accountId);
		}
		acc.setBalance(acc.getBalance() + amount);*/
	}


	/** Starts a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void beginTransaction() throws RemoteException, DataAccessException {
		// transactions not implemented, modifications are committed directly
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
		} catch(ClassNotFoundException cnfe) {
			//TODO
		} catch(SQLException sqle) {
			//TODO
		}
	}


	/** Commits a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void commitTransaction() throws RemoteException, DataAccessException {
		// transactions not implemented, modifications are committed directly
		try { 
			conn.commit();
			conn.close();
		} catch(SQLException sqle) {
			//TODO
		}
	}


	/** Rolls back a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void rollbackTransaction() throws RemoteException, DataAccessException {
		// transactions not implemented, modifications are committed directly
		try { 
			conn.rollback();
			conn.close();
		} catch(SQLException sqle) {
			//TODO
		}
	}

}
