package bank.banking;

import java.net.MalformedURLException;
import java.rmi.*;

import bank.Account;
import bank.Bank;
import bank.interbanking.Interbank;

/**
 * Transactor used by the bank.application.*Servlet to do transactions.
 * Calls remote TransactionProcessors when needed.
 */

public class Transactor implements Transaction {

	private TransactionProcessing local;
	private Interbank interbank;
	
	public Transactor(TransactionProcessing local, Interbank interbank) {
		this.local = local;
		this.interbank = interbank;
	}
		
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
	public String transfer(String debitAccountId, String creditAccountId, float amount)
		throws RemoteException, TransactionException {
		if(isLocal(creditAccountId) && isLocal(debitAccountId)) {
			return local.transfer(debitAccountId, creditAccountId, amount);
		} else if (isLocal(debitAccountId)){
			TransactionProcessing debitProc = local;
			TransactionProcessing creditProc = getProcessor(creditAccountId);
			debitProc.prepareDebit(debitAccountId, creditAccountId, amount);
			creditProc.prepareCredit(creditAccountId, debitAccountId, amount);
			debitProc.commit();
			creditProc.commit();
			return null;
		} else {
			return "At least one of the accounts should be local";
		}
	}
	
	/** Deposits some 'amount' of money to account 'creditAccountId'.
     * @param creditAccountId credit account id
     * @param amount amount of money to be deposited
	 * @return 'null' if the deposit succeeds; otherwise, the reason for
	 * rejecting the deposit is returned
     * @throws RemoteException
     * @throws TransactionException
	 */
	public String deposit(String creditAccountId, float amount)
		throws RemoteException, TransactionException {
		return getProcessor(creditAccountId).deposit(creditAccountId, amount);
	}
	
	/** Withdraws some 'amount' of money from account 'debitAccountId'.
     * @param debitAccountId debit account id
     * @param amount amount of money to be deposited
	 * @return 'null' if the withdrawal succeeds; otherwise, the reason for
	 * rejecting the withdrawal is returned
     * @throws RemoteException
     * @throws TransactionException
	 */
	public String withdraw(String debitAccountId, float amount)
		throws RemoteException, TransactionException {
		return getProcessor(debitAccountId).withdraw(debitAccountId, amount);
	}

	/** Returns the balance of account 'accountId'.
     * @param accountId account id
     * @return balance
     * @throws RemoteException
     * @throws TransactionException
	 */
	public float getBalance(String accountId)
		throws RemoteException, TransactionException {
		return getProcessor(accountId).getBalance(accountId);
	}

	private boolean isLocal(String accountId) {
		return accountId.startsWith(Bank.getBankCode());
	}
	private TransactionProcessing getProcessor(String accountId) throws RemoteException,TransactionException {
		if(isLocal(accountId)) {
			return local;
		} else {
			String bankCode = Account.getBankCode(accountId);
			try {
				String addr = interbank.lookupTransactionProcessor(bankCode);
				if(addr == null)
					throw new TransactionException("Can't find Transactionprocessor for account " + accountId + " (bankcode "+bankCode+")");
				return (TransactionProcessing)Naming.lookup(addr/*.substring("rmi:".length())*/);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new TransactionException(e.getMessage());
			} catch (NotBoundException e) {
				e.printStackTrace();
				throw new TransactionException(e.getMessage());
			}
		}
	}

}
