package bank.authentication;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Naming;
import java.net.MalformedURLException;

import bank.Account;
import bank.BankCard;
import bank.Client;
import bank.Bank;
import bank.access.DataAccess;
import bank.access.DataAccessException;
import bank.interbanking.Interbank;

public class Authenticator implements Authentication {
	private DataAccess db;
	private Interbank ib;
	
	public Authenticator(DataAccess db, Interbank ib) {
		this.db = db;
		this.ib = ib;
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
			//e.printStackTrace();
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
		//int i = 0;
		if(cardId == null || PIN == null) { return null; }
		String bankcode = cardId.substring(0, BankCard.BANK_CODE_LENGTH);
		if(cardId.startsWith(Bank.getBankCode())) {
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
		else {
			System.out.println("Card " + cardId + " not a local card, trying to get a remote authenticator.");
			String remoteAuthLoc = ib.lookupAuthenticator(bankcode);
			System.out.println("Got remote auth location: " + remoteAuthLoc);
			if(remoteAuthLoc != null) {
				try {
					Authentication remoteauth = (Authentication) Naming.lookup(remoteAuthLoc);
					return remoteauth.authenticateCDClient(cardId, PIN);
				} catch(MalformedURLException e) {
					e.printStackTrace();
					throw new AuthenticationException("MalformedURL: " + e.getMessage());
				} catch(NotBoundException e) {
					e.printStackTrace();
					throw new AuthenticationException("NotBound: " + e.getMessage());
				}
			} else throw new AuthenticationException("Could not resolve "+bankcode+" from interbank");
		}
	}
}
