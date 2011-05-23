package bank.authentication;

import java.rmi.RemoteException;

import bank.Account;
import bank.BankCard;
import bank.Client;
import bank.access.DataAccess;
import bank.access.DataAccessException;

public class Authenticator implements Authentication {
	private DataAccess db;
	
	public Authenticator(DataAccess db) {
		this.db = db;
	}
	
	/** Checks the authentication of a client of the home banking application.
     * @param username user name
     * @param password password
	 * @return client information if the username and password match; 'null', otherwise
     * @throws RemoteException
     * @throws AuthenticationException
	 */
	public Client authenticateHBClient(String username, String password) throws RemoteException, AuthenticationException {
		try {
			Client cl = db.getClient(username);
			if(!password.equals(cl.getPassword())) {
				cl = null;
			}
			return cl;
		} catch (DataAccessException e) {
			// Maybe we should just make up a generic error message here.
			// Instead of potentially leaking information about (existing or not) usernames and/or passwords.
			throw new AuthenticationException(e.getMessage());
		}
	}

	/** Checks the authentication of a client of the cash dispensing application.
     * @param cardId card id
     * @param PIN pin
	 * @return client information if the username and password match; 'null', otherwise
     * @throws RemoteException
     * @throws AuthenticationException
	 */
	public Client authenticateCDClient(String cardId, String PIN) throws RemoteException, AuthenticationException {
		try {
			BankCard card = db.getBankCard(cardId);
			Client cl = null;
			if(card.getPIN().equals(PIN)) {
				Account acc = db.getAccount(card.getAccountId());
				cl = db.getClient(acc.getUserName());
			}
			return cl;
		} catch (DataAccessException e) {
			// Maybe we should just make up a generic error message here.
			// Instead of potentially leaking information about (existing or not) usernames and/or passwords.
			throw new AuthenticationException(e.getMessage());
		}
	}
}
