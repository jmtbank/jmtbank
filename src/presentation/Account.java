package presentation;

import java.io.Serializable;

public class Account implements Serializable {
	private String accountId;
	private float balance;
	
	public Account() {
		balance = 0f;
	}
	public Account(String accountId, float balance) {
		this.accountId = accountId;
		this.balance = balance;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}

}
