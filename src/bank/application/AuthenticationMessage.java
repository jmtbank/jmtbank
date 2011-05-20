package bank.application;

import java.io.Serializable;

import bank.authentication.AuthenticationException;


public class AuthenticationMessage implements Serializable {
	private String message;

	public AuthenticationMessage() {}
	public AuthenticationMessage(String message) {
		this.setMessage(message);
	}
	public AuthenticationMessage(AuthenticationException e) {
		this.message = e.getMessage();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
