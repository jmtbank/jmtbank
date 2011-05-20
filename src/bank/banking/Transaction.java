package bank.banking;

/**
 * <p>Title:        Interface TransactionManager</p>
 * <p>Description:  Provided by the transaction processing sub-system.
 *                  Allows the application layer to obtain balance information
 *                  and to transfer money between accounts.</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel, L. Ferreira Pires
 * @version 1.0
 */

import java.rmi.*;

public interface Transaction extends Remote {

	/** Transfers some 'amount' of money from account 'debitAccountId'
	 * to account 'creditAccountId'.
     * @param debitAccountId debit account id
     * @param creditAccountId credit account id
     * @param amount amount of money to be transferred
	 * @return 'null' if the transfer succeeds; otherwise, the reason for
	 * rejecting the transfer is returned
     * @throws RemoteException
     * @throws TransactionException
	 */
	public String transfer(String debitAccountId,
						   String creditAccountId,
						   float amount)
		throws RemoteException, TransactionException;
	
	/** Deposits some 'amount' of money to account 'creditAccountId'.
     * @param creditAccountId credit account id
     * @param amount amount of money to be deposited
	 * @return 'null' if the deposit succeeds; otherwise, the reason for
	 * rejecting the deposit is returned
     * @throws RemoteException
     * @throws TransactionException
	 */
	public String deposit(String creditAccountId,
						   float amount)
		throws RemoteException, TransactionException;
	
	/** Withdraws some 'amount' of money from account 'debitAccountId'.
     * @param debitAccountId debit account id
     * @param amount amount of money to be deposited
	 * @return 'null' if the withdrawal succeeds; otherwise, the reason for
	 * rejecting the withdrawal is returned
     * @throws RemoteException
     * @throws TransactionException
	 */
	public String withdraw(String debitAccountId,
						   float amount)
		throws RemoteException, TransactionException;

	/** Returns the balance of account 'accountId'.
     * @param accountId account id
     * @return balance
     * @throws RemoteException
     * @throws TransactionException
	 */
	public float getBalance(String accountId)
		throws RemoteException, TransactionException;

}