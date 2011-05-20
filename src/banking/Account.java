package banking;

import java.util.Map;
import java.util.HashMap;

public class Account extends bank.application.presentation.Account {
	private static Map<String,Account> accounts;
	static {
		accounts = new HashMap<String,Account>();
		accounts.put("ac1",
				new Account("ac1",0));
		accounts.put("ac2",
				new Account("ac2",100.0f));
		accounts.put("ac3",
				new Account("ac3",-100.0f));
	}
	
	private String accountId;
	private float balance;

	public Account() {
		super();
	}
	public Account(String accountId, float balance) {
		super(accountId,balance);
	}

	public static Account getAccount(String accountId) {
		return accounts.get(accountId);
	}
}
