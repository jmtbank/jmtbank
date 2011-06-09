package bank.authentication;

import java.rmi.ConnectException;
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
		System.out.print("Authentication user " + username + ": ");
		try {
			Client cl = db.getClient(username);
			if(!password.equals(cl.getPassword())) {
				cl = null;
			}
			System.out.println(cl != null ? "OK" : "fail");
			return cl;
		} catch (DataAccessException e) {
			// Maybe we should just make up a generic error message here.
			// Instead of potentially leaking information about (existing or not) usernames and/or passwords.
			System.out.println("fail (dataAccess");
			throw new AuthenticationException(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("fail (nullpointer)");
			e.printStackTrace();
			return null;
		} catch (ConnectException e) {
			System.out.println("fail (connectexception)");
			System.err.println("Connection to the database failed. You should restart this server");
			return null;
		} catch (RemoteException e) {
			System.out.println("fail (remoteexception)");
			e.printStackTrace();
			return null;
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
		System.out.print("Authentication card " + cardId + ": ");
		try {
			BankCard card = db.getBankCard(cardId);
			Client cl = null;
			if(card.getPIN().equals(PIN)) {
				Account acc = db.getAccount(card.getAccountId());
				cl = db.getClient(acc.getUserName());
			}
			System.out.println(cl != null ? "OK" : "fail");
			return cl;
		} catch (DataAccessException e) {
			// Maybe we should just make up a generic error message here.
			// Instead of potentially leaking information about (existing or not) usernames and/or passwords.
			System.out.println("fail (dataAccess");
			throw new AuthenticationException(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("fail (nullpointer)");
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			System.out.println("fail (remoteexception)");
			e.printStackTrace();
			return null;
		}
	}
}
