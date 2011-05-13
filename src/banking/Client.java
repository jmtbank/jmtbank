package banking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

	private static final List<Client> clients;
	static {
		clients = new ArrayList<Client>();
		String[] accounts = {"ac1","ac2"};
		clients.add(new Client("cl1", "client1", "password1","card1","0001", accounts));
		accounts = new String[1];
		accounts[0] = "ac3";
		clients.add(new Client("cl2", "client2", "password2","card2","0002", accounts));
	}
	private String clientId;
	private String username;
	private String password;
	private String cardId;
	private String pin;
	private String[] accounts = {"een","Twee"};
	
	public Client() {}	
	public Client(String clientId, String username, String password,
			String cardId, String pin, String[] accounts) {
		this.clientId = clientId;
		this.username = username;
		this.password = password;
		this.cardId = cardId;
		this.pin = pin;
		this.accounts = accounts;
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String[] getAccounts() {
		return accounts;
	}
	public void setAccounts(String[] accounts) {
		this.accounts = accounts;
	}

	public static Iterable<Client> getClients() {
		return clients;
	}
}