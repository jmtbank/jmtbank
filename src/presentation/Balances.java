package presentation;

import java.io.Serializable;
import java.util.List;

public class Balances implements Serializable {
	private String clientId;
	private List<Account> accounts;

	public Balances() {}
	public Balances(String clientId, List<Account> accounts) {
		this.clientId = clientId;
		this.accounts = accounts;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
