package bank;

/**
 * <p>Title:        Class Account</p>
 * <p>Description:  Represents a bank account</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

public class Account implements java.io.Serializable {

	// constants

	public final static int ACCOUNT_ID_LENGTH   = 10;
    public final static int BANK_CODE_LENGTH    = 3;
    public final static int USERNAME_LENGTH     = 10;

	// attributes

	private String accountId;   // identifier of this account
	private String userName;    // identifies owner of this account
	private float balance;      // balance of this account

    // constructors

	/** Constructs an account object
     */
    public Account() {
    }

	/**Constructs an account object
     * @param accountId account identifier
     * @param userName user name of bank client that owns the account
     * @param balance account balance
     */
    public Account(String accountId, String userName, float balance) {
		this.accountId = accountId;
		this.userName = userName;
		this.balance = balance;
	}

	/** Gets the identifier of this account
     * @return account identifier
     */
    public String getAccountId() {
		return this.accountId;
	}

	/** Sets the identifier of this account
     * @param accountId account identifier
     */
    public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/** Gets the user name of the client that owns this account
     * @return user name
     */
    public String getUserName() {
		return this.userName;
	}

	/** Sets the user name of the client that owns this account
     * @param userName user name
     */
    public void setUserName(String userName) {
		this.userName = userName;
	}

    /** Gets the balance of this account
     * @return account balance
     */
	public float getBalance() {
		return this.balance;
	}

	/** Sets the balance of this account
     * @param balance account balance
     */
    public void setBalance(float balance) {
		this.balance = balance;
	}
}