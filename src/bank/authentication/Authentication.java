package bank.authentication;

/**
 * <p>Title:        Interface Authenticator</p>
 * <p>Description:  Provided by the authentication sub-system.
 *                  Allows the application layer to check the authentication of
 *                  a cash dispensing user or a home banking user.</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel, L. Ferreira Pires
 * @version 1.0
 */

import bank.*;
import java.rmi.*;

public interface Authentication extends Remote {

	/** Checks the authentication of a client of the home banking application.
     * @param username user name
     * @param password password
	 * @return client information if the username and password match; 'null', otherwise
     * @throws RemoteException
     * @throws AuthenticationException
	 */
	public Client authenticateHBClient(String username, String password)
		throws RemoteException, AuthenticationException;

	/** Checks the authentication of a client of the cash dispensing application.
     * @param cardId card id
     * @param PIN pin
	 * @return client information if the username and password match; 'null', otherwise
     * @throws RemoteException
     * @throws AuthenticationException
	 */
	public Client authenticateCDClient(String cardId, String PIN)
		throws RemoteException, AuthenticationException;

}