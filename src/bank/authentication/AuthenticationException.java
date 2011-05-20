package bank.authentication;

/**
 * <p>Title:        Class AuthenticationException</p>
 * <p>Description:  An AuthenticationException is thrown when a problem
 *                  occurs during the process of checking
 *                  the authentication of some user.</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

public class AuthenticationException extends Exception {

    public AuthenticationException() {
    }

	public AuthenticationException(String s) {
		super(s);
	}
}