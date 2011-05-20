package bank;

/**
 * <p>Title:        Class Client</p>
 * <p>Description:  Represents a bank client</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

import java.util.*;

public class Client implements java.io.Serializable {

	// constants

	public final static int USERNAME_LENGTH         = 10;
	public final static int MIN_PASSWORD_LENGTH     = 6;
	public final static int MAX_PASSWORD_LENGTH     = 10;

	// attributes

	private String userName;         // username of this client
	private String password;         // password for home banking application
	private String accountId;        // identifier of account owned by this client
    private String firstName;        // first name of this client
    private String lastName;         // last name of this client

    // constructors

	/** Constructs a client object
     */
    public Client() {
    }

	/** Constructs a client object
     * @param userName user name of bank client
     * @param password password used for home banking application
     * @param accountId account identifier
     */
    public Client(String userName, String password, String accountId) {
		this.userName = userName;
		this.password = password;
		this.accountId = accountId;
	}

	/** Constructs a client object
     * @param userName user name of bank client
     * @param password password used for home banking application
     * @param accountId account identifier
     * @param firstName first name of bank client
     * @param lastName last name of bank client
     */
    public Client(String userName, String password, String accountId,
                  String firstName, String lastName) {
		this(userName, password, accountId);
        this.firstName = firstName;
        this.lastName = lastName;
	}

	/** Gets the user name of this bank client
     * @return user name
     */
    public String getUserName() {
		return this.userName;
	}

	/** Sets the user name of this bank client
     * @param userName user name
     */
    public void setUserName(String userName) {
		this.userName = userName;
	}

	/** Gets the password of this bank client
     * @return password
     */
    public String getPassword() {
		return this.password;
	}

	/** Sets the password of this bank client
     * @param password password
     */
    public void setPassword(String password) {
		this.password = password;
	}

	/** Gets the identifier of the account of this bank client
     * @return account identifier
     */
    public String getAccountId() {
		return this.accountId;
	}

	/** Sets the identifier of the account of this bank client
     * @param accountId account identifier
     */
    public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/** Gets the first name of this bank client
     * @return first name
     */
    public String getFirstName() {
		return this.firstName;
	}

    /** Sets the first name of this bank client
     * @param firstName first name
     */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/** Gets the last name of this bank client
     * @return last name
     */
	public String getLastName() {
		return this.lastName;
	}

    /** Sets the last name of this bank client
     * @param lastName last name
     */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}