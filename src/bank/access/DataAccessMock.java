package bank.access;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import bank.Account;
import bank.BankCard;
import bank.Client;

public class DataAccessMock implements DataAccess {
	private Map<String,Account> accounts;
	private Map<String,Client> clients;
	private Map<String,BankCard> cards;

	public DataAccessMock() {
		final int n = 5; // niet meer dan 9 van maken
		accounts = new HashMap<String,Account>();
		clients = new HashMap<String,Client>();
		cards = new HashMap<String,BankCard>();
		for(int i = 1; i <= n; i++) {
			accounts.put("001000000"+i, new Account("001000000"+i,"user"+i,i*1000));
			clients.put("user"+i, new Client("user"+i,"password"+i,"001000000"+i));
			cards.put("001000000"+i, new BankCard("001000000"+i,"001000000"+i,"0000"+i));
		}
	}

	/** Retrieves account information.
     * @param accountId account id
     * @return information about the account identified by 'accountId',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public Account getAccount(String accountId) throws RemoteException, DataAccessException {
		return accounts.get(accountId);
	}

	/** Retrieves client information
     * @param username user name
     * @return information about the client identified by 'username',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public Client getClient(String username) throws RemoteException, DataAccessException {
		return clients.get(username);
	}


	/** Retrieves bank card information
     * @param cardId card id
     * @return information about the bank card identified by 'cardId',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public BankCard getBankCard(String cardId) throws RemoteException, DataAccessException {
		return cards.get(cardId);
	}


	/** Subtracts amount 'amount' from the balance of the account identified
	 * by 'accountId'
     * @param accountId account id
     * @param amount amount to be subtracted
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void debitAccount(String accountId, float amount) throws RemoteException, DataAccessException {
		Account acc = accounts.get(accountId);
		if(acc == null) {
			throw new DataAccessException("Unknown account: " + accountId);
		}
		acc.setBalance(acc.getBalance() - amount);
	}


	/** Adds amount 'amount' to the balance of the account identified
	 * by 'accountId'
     * @param accountId account id
     * @param amount amount to be added
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void creditAccount(String accountId, float amount) throws RemoteException, DataAccessException {
		Account acc = accounts.get(accountId);
		if(acc == null) {
			throw new DataAccessException("Unknown account: " + accountId);
		}
		acc.setBalance(acc.getBalance() + amount);
	}


	/** Starts a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void beginTransaction() throws RemoteException, DataAccessException {
		// transactions not implemented, modifications are committed directly
	}


	/** Commits a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void commitTransaction() throws RemoteException, DataAccessException {
		// transactions not implemented, modifications are committed directly
	}


	/** Rolls back a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void rollbackTransaction() throws RemoteException, DataAccessException {
		// transactions not implemented, modifications are committed directly
	}

}
