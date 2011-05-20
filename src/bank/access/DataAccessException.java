package bank.access;

/**
 * <p>Title:        Class DataAccessException</p>
 * <p>Description:  A DataAccessException is thrown when a problem
 *                  occurs during the process of retrieving or updating
 *                  information in the bank's database.</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

public class DataAccessException extends Exception {

    public DataAccessException() {
    }

	public DataAccessException(String s) {
		super(s);
	}
}