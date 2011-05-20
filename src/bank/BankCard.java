package bank;

/**
 * <p>Title:        Class BankCard</p>
 * <p>Description:  Represents a bank card</p>
 * <p>Copyright:    Copyright (c) 2002</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel
 * @version 1.0
 */

public class BankCard implements java.io.Serializable {

	// constants

	public final static int BANK_CARD_ID_LENGTH = 10;
    public final static int BANK_CODE_LENGTH    = 3;
	public final static int PIN_LENGTH          = 5;

	// attributes

	private String cardId;      // identifier of this card
	private String accountId;   // account associated with this card
	private String pin;         // PIN associated with this card

    // constructors

	/** Constructs a bank card object
     */
    public BankCard() {
    }

	/** Constructs a bank card object
     * @param cardId card identifier
     * @param accountId identifier of the associated account
     * @param pin pin code
     */
    public BankCard(String cardId, String accountId, String pin) {
		this.cardId = cardId;
		this.accountId = accountId;
		this.pin = pin;
	}
    
	/** Gets identifier of this bank card
     * @return bank card identifier
     */
    public String getCardId() {
		return this.cardId;
	}

    /** Sets the identifier of this bank card
     * @param cardId bank card identifier
     */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

    /** Gets the identifier of the account associated with this bank card
     * @return account identifier
     */
	public String getAccountId() {
		return this.accountId;
	}

	/** Sets the identifier of the account associated with this bank card
     * @param accountId account identifier
     */
    public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

    /** Gets the pin code associated with this bank card
     * @return pin code
     */
	public String getPIN() {
		return this.pin;
	}

    /** Sets the pin code associated with this bank card
     * @param pin pin code
     */
	public void setPIN(String pin) {
		this.pin = pin;
	}
}