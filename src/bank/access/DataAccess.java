package bank.access;

/**
 * <p>Title:        Interface DataAccessor</p>
 * <p>Description:  Provided by the DataAccess sub-system.
 *                  Allows the banking and application layer to access information
 *                  from the bank's database.</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel, L. Ferreira Pires
 * @version 1.0
 */

import bank.*;
import bank.banking.*;
import java.rmi.*;

public interface DataAccess extends Remote {

	/** Retrieves account information.
     * @param accountId account id
     * @return information about the account identified by 'accountId',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public Account getAccount(String accountId)
		throws RemoteException, DataAccessException;

	/** Retrieves client information
     * @param username user name
     * @return information about the client identified by 'username',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public Client getClient(String username)
		throws RemoteException, DataAccessException;

	/** Retrieves bank card information
     * @param cardId card id
     * @return information about the bank card identified by 'cardId',
	 * or 'null' in case this information can not be found.
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public BankCard getBankCard(String cardId)
		throws RemoteException, DataAccessException;

	/** Subtracts amount 'amount' from the balance of the account identified
	 * by 'accountId'
     * @param accountId account id
     * @param amount amount to be subtracted
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void debitAccount(String accountId, float amount)
		throws RemoteException, DataAccessException;

	/** Adds amount 'amount' to the balance of the account identified
	 * by 'accountId'
     * @param accountId account id
     * @param amount amount to be added
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void creditAccount(String accountId, float amount)
		throws RemoteException, DataAccessException;

	/** Starts a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void beginTransaction()
		throws RemoteException, DataAccessException;

	/** Commits a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void commitTransaction()
		throws RemoteException, DataAccessException;

	/** Rolls back a transaction
     * @throws RemoteException
     * @throws DataAccessException
	 */
	public void rollbackTransaction()
		throws RemoteException, DataAccessException;

}