package bank.banking;

/**
 * <p>Title:        Interface TransactionProcessor</p>
 * <p>Description:  Provided by the transaction processing system.
 *                  Allows the banking layer from this and other bank systems
 *                  to obtain balance information and to transfer money between
 *                  accounts.</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

import java.rmi.*;

public interface TransactionProcessing extends Transaction {

	/** Prepares the subtraction of some 'amount' of money from account
	 * 'debitAccountId'.
     * @param debitAccountId debit account id
     * @param creditAccountId credit account id (can be used for logging purposes)
     * @param amount amount of money to be subtracted
     * @throws RemoteException
     * @throws TransactionException
	 */
	public void prepareDebit(String debitAccountId,
						     String creditAccountId,
							 float amount)
		throws RemoteException, TransactionException;

	/** Prepares the addition of some 'amount' of money to account
	 * 'creditAccountId'.
     * @param creditAccountId credit account id
     * @param debitAccountId debit account id (can be used for logging purposes)
     * @param amount amount of money to be added
     * @throws RemoteException
     * @throws TransactionException
	 */
	public void prepareCredit(String creditAccountId,
						      String debitAccountId,
							  float amount)
		throws RemoteException, TransactionException;

	/** Commits all prepared debits or credits since the last commit/rollback.
	 * Normally only a single debit or credit is committed.
     * @throws RemoteException
     * @throws TransactionException
	 */
	public void commit()
		throws RemoteException, TransactionException;

	/** Rolls back all prepared debits or credits since the last commit/rollback.
	 * Normally only a single debit or credit is rolled back.
     * @throws RemoteException
     * @throws TransactionException
	 */
	public void rollback()
		throws RemoteException, TransactionException;
}