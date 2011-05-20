package bank.banking;

/**
 * <p>Title:        Class TransactionException</p>
 * <p>Description:  A TransactionException is thrown when a problem
 *                  occurs during the processing of a transaction.</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

public class TransactionException extends Exception {

    public TransactionException() {
    }

	public TransactionException(String s) {
		super(s);
	}
}