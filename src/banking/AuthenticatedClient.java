package banking;

import java.io.Serializable;

public class AuthenticatedClient implements Serializable {
	private String clientId;
	private AuthenticationMethod method;

	public AuthenticatedClient() {}
	public AuthenticatedClient(String clientId, AuthenticationMethod method) {
		this.clientId = clientId;
		this.method = method;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public AuthenticationMethod getMethod() {
		return method;
	}
	public void setMethod(AuthenticationMethod method) {
		this.method = method;
	}
}

